package org.microcks.web;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.microcks.model.node.openapi.NodeRootOpenApiBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class UploadArtifactController {

	@Value( "${keycloak.realm}" )
	private String keycloakrealm;

	@Value( "${keycloak.resource}" )
	private String keycloakresource;

	@Value( "${keycloak.auth-server-url}" )
	private String keycloakauthserverurl;
	
	
	@Autowired
	private NodeRootOpenApiBean root;
	
	/** A simple logger for diagnostic messages. */
	private static Logger log = LoggerFactory.getLogger(UploadArtifactController.class);

	@RequestMapping(value = "/artifact/upload", method = RequestMethod.POST)
	public ResponseEntity<?> importArtifact(@RequestParam(value = "file") MultipartFile file) {
		log.info("## Recibiendo archivo con las definiciones");
		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(file.getInputStream(), writer, StandardCharsets.UTF_8);
			String yaml = writer.toString();
			root.load(yaml);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@ResponseBody
	@RequestMapping(value = "keycloak/config", method = RequestMethod.GET, produces = "application/json")
	public String getKeyclockConfig() {
		String config = "{\"realm\":\""+keycloakrealm+"\",\"resource\":\""+keycloakresource+"-js\",\"auth-server-url\":\""+keycloakauthserverurl+"\",\"ssl-required\":\"external\",\"public-client\":true}";
		System.out.println("## config keyclock/config: " + config);
	    return config;
	}
	//{"realm":"microcks","resource":"microcks-app-js","auth-server-url":"http://localhost:8180/auth","ssl-required":"external","public-client":true}
}