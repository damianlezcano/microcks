/*
 * Licensed to Laurent Broudoux (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Author licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.microcks.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.microcks.domain.TestResult;
import org.microcks.domain.TestRunnerType;
import org.microcks.model.node.Node;
import org.microcks.model.node.openapi.NodeRootOpenApiBean;
import org.microcks.model.node.openapi.NodeVersionOpenApiBean;
import org.microcks.model.node.swagger.NodeRootSwaggerBean;
import org.microcks.model.node.swagger.NodeVersionSwaggerBean;
import org.microcks.web.dto.TestRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * A Rest controller for API defined on test results.
 * 
 * @author laurent
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class TestController {

	/** A simple logger for diagnostic messages. */
	private static Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private NodeRootOpenApiBean root;

	private Map<String, Entry<Node,TestResult>> th = new HashMap<String, Entry<Node,TestResult>>();

	@RequestMapping(value = "/tests", method = RequestMethod.POST)
	public ResponseEntity<TestResult> createTest(@RequestBody TestRequestDTO test) throws Exception {
		try {
			log.debug("Creating new test for {} on endpoint {}", test.getServiceId(), test.getTestEndpoint());
			// serviceId may have the form of <service_name>:<service_version>
			String name = test.getServiceId().substring(0, test.getServiceId().indexOf(':'));
			String version = test.getServiceId().substring(test.getServiceId().indexOf(':') + 1);
			NodeVersionOpenApiBean nodeVersion = (NodeVersionOpenApiBean) root.get(name).get(version);
			TestResult testResult = createWorkerTests(nodeVersion, test.getTestEndpoint());
			return new ResponseEntity<TestResult>(testResult, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception("Service (" + test.getServiceId() + ") not found in nodeROOT");
		}
	}

	@RequestMapping(value = "/tests/{id}", method = RequestMethod.GET)
	public ResponseEntity<TestResult> getTestResult(@PathVariable("id") String testResultId) {
		log.debug("Getting TestResult with id {}", testResultId);
		NodeVersionOpenApiBean nodeVersionOpenApi = null;
		TestResult testResult = null;
		try {
			Entry<Node,TestResult> entry = th.get(testResultId);
			nodeVersionOpenApi = (NodeVersionOpenApiBean) entry.getKey();
			testResult = entry.getValue();
			//------------------------------------------------------------
			String json = httpGET(testResult.getTestedEndpoint());
			
			Node nodeRootSwagger = new NodeRootSwaggerBean(json);
			
			Node nodeVersionSwagger = nodeRootSwagger.get(nodeVersionOpenApi.getParent().getName()).get(nodeVersionOpenApi.getName());
			
			if(nodeVersionOpenApi.equals(nodeVersionSwagger)) {
				testResult.setInProgress(false);
				testResult.setSuccess(true);
			}else {
				log.error("## Error OpenApi != Swagger20 in " + testResult.getTestedEndpoint());
				testResult.setInProgress(false);
				testResult.setSuccess(false);
			}
			th.remove(testResultId);
			return new ResponseEntity<>(testResult, HttpStatus.OK);			
		} catch (Exception e) {
			log.error("Remote (" + testResult.getTestedEndpoint() + ") swagger not found for testing");
			testResult = buildTestResult(null, testResultId, false, false);
			return new ResponseEntity<TestResult>(testResult,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ------------------------------------------------------------------------------

	public TestResult createWorkerTests(NodeVersionOpenApiBean nodeVersion, String testEndpoint) {
		TestResult testResult = buildTestResult(testEndpoint, String.valueOf(System.currentTimeMillis()),true,false);
		Map<Node,TestResult> map = new HashMap<Node, TestResult>();
		map.put(nodeVersion,testResult);
		Entry<Node,TestResult> entry = map.entrySet().iterator().next();
		th.put(testResult.getServiceId(), entry);
		return testResult;
	}

	private TestResult buildTestResult(String endpoint, String serviceId, boolean inProgress, boolean success) {
		TestResult testResult = new TestResult();
		testResult.setTestDate(new Date());
		testResult.setTestedEndpoint(endpoint);
		String id = String.valueOf(System.currentTimeMillis());
		testResult.setId(id);
		testResult.setServiceId(id);
		testResult.setRunnerType(TestRunnerType.OPEN_API_SCHEMA);
		testResult.setInProgress(inProgress);
		testResult.setSuccess(success);
		return testResult;
	}
	
	protected String httpGET(String path) throws Exception {
		String response = "";
		try {
			URL url = new URL(path);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			
			int statusCode = conn.getResponseCode();
			if (statusCode >= 200 && statusCode < 400) {
				response = extract(conn.getInputStream());
			}else {
				response = extract(conn.getErrorStream());
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error en la llamada GET");
		}
		return response;
	}

	private String extract(InputStream is) throws Exception {
		Reader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuilder data = new StringBuilder();
		for (int c; (c = in.read()) >= 0;) {
			data.append((char) c);
		}
		return data.toString();
	}


}