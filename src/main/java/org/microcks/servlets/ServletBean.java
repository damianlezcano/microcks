package org.microcks.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.microcks.model.NodeMap;
import org.microcks.model.node.openapi.NodeMethodOpenApiBean;
import org.microcks.model.node.openapi.NodeRootOpenApiBean;
import org.microcks.repository.RepositoryBean;

public class ServletBean extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private NodeRootOpenApiBean root;	
	
	public ServletBean(NodeRootOpenApiBean root) {
		this.root = root;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		new BaseProcesor(root, request, response, "get") {
			@Override
			public void exec(NodeMethodOpenApiBean nodeMethod, Map<String,Object> pathParam, Map<String,Object> queryParam, RepositoryBean repository) throws Exception {
				NodeMap nType = nodeMethod.getNodeVersion().getSchema().get("type");
				String type = nType.r() == null ? "string" : nType.str();
				
				List<Map> map = repository.get(pathParam,queryParam);
				
				//status code
				Entry entryCode = (Entry) nodeMethod.getResponses().entrySet().iterator().next();
				response.setStatus(Integer.valueOf(entryCode.getKey().toString()));
				Map example = new NodeMap(entryCode.getValue()).lookup("examples").map();
				//contentType
				Entry entryExample = (Entry) example.entrySet().iterator().next();
				response.setContentType("application/json");
				//response
				String json = "";
				if(pathParam.isEmpty()) {
					if(map.isEmpty()) {
						json = objectToJson(entryExample.getValue());
					}else{
						json = mapToJson(map);					
					}
				}else{
					if(map.isEmpty()) {
						json = objectToJson(entryExample.getValue());
					}else{
						json = objectToJson(map.get(0));						
					}
				}
				PrintWriter out = response.getWriter();
				out.println(json);
			
			}
		}.run();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new BaseProcesor(root, request, response, "post") {
			@Override
			public void exec(NodeMethodOpenApiBean nodeMethod, Map<String,Object> pathParam, Map<String,Object> queryParam, RepositoryBean repository) throws Exception {
			    String json = getBody(request);
		        
			    Map<String, String> map = jsonToMap(json);
				repository.post(map,pathParam,queryParam);
				//status code
				Entry entryCode = (Entry) nodeMethod.getResponses().entrySet().iterator().next();
				response.setStatus(Integer.valueOf(entryCode.getKey().toString()));
				Map example = new NodeMap(entryCode.getValue()).lookup("examples").map();
				//contentType
				Entry entryExample = (Entry) example.entrySet().iterator().next();
				response.setContentType("application/json");
				//response
				PrintWriter out = response.getWriter();
				out.println(objectToJson(entryExample.getValue()));
			}
		}.run();
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new BaseProcesor(root, request, response, "put") {
			@Override
			public void exec(NodeMethodOpenApiBean nodeMethod, Map<String,Object> pathParam, Map<String,Object> queryParam, RepositoryBean repository) throws Exception {
			    String json = getBody(request);
		        
			    Map<String, String> map = jsonToMap(json);
				repository.put(map,pathParam,queryParam);
				
				NodeMap nodeMap = new NodeMap(nodeMethod.getResponses());
				//response.setContentType(nodeMap.get("202").get("ContentType").str());
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
			}
		}.run();
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new BaseProcesor(root, request, response, "delete") {
			@Override
			public void exec(NodeMethodOpenApiBean nodeMethod, Map<String,Object> pathParam, Map<String,Object> queryParam, RepositoryBean repository) throws Exception {
				repository.delete(pathParam,queryParam);
				
				NodeMap nodeMap = new NodeMap(nodeMethod.getResponses());
				//response.setContentType(nodeMap.get("200").get("ContentType").str());
				PrintWriter out = response.getWriter();
			}
		}.run();
	}
	
}

