package org.microcks.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.microcks.exception.DuplicateEntityException;
import org.microcks.exception.MethodNotFoundException;
import org.microcks.exception.PathParamNotFoundException;
import org.microcks.exception.QueryParamNotFoundException;
import org.microcks.exception.ShowSimpleTextException;
import org.microcks.model.NodeMap;
import org.microcks.model.node.Node;
import org.microcks.model.node.openapi.NodeMethodOpenApiBean;
import org.microcks.model.node.openapi.NodePathOpenApiBean;
import org.microcks.model.node.openapi.NodeRootOpenApiBean;
import org.microcks.model.node.openapi.NodeServiceOpenApiBean;
import org.microcks.model.node.openapi.NodeVersionOpenApiBean;
import org.microcks.repository.RepositoryBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseProcesor {
	
	Node root;
	HttpServletRequest request;
	HttpServletResponse response;
	String method;
	
	public BaseProcesor(Node root, HttpServletRequest request, HttpServletResponse response, String method) {
		this.root = root;
		this.request = request;
		this.response = response;
		this.method = method;
	}
	
	public void run() throws IOException {
		try {

			Map<String,Object> rMap = p(request, response,method);
			NodeMethodOpenApiBean nodeMethod = (NodeMethodOpenApiBean) rMap.get("nodeMethod");
			Map<String,Object> pathParam = (Map<String, Object>) rMap.get("pathParam");
			Map<String,Object> queryParam = (Map<String, Object>) rMap.get("queryParam");
			
			//------------------------------------------------------
			RepositoryBean repository = nodeMethod.getRepository();
			exec(nodeMethod, pathParam, queryParam, repository);
		} catch (MethodNotFoundException e) {
			response.setStatus(Response.SC_NOT_FOUND);
		} catch (PathParamNotFoundException e) {
			response.setStatus(Response.SC_NOT_IMPLEMENTED);
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(e.getCause().getMessage());
		} catch (QueryParamNotFoundException e) {
			response.setStatus(Response.SC_NOT_IMPLEMENTED);
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(e.getCause().getMessage());
		}catch (ShowSimpleTextException e) {
			response.setStatus(Response.SC_OK);
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(e.getMessage());
		}catch (DuplicateEntityException e) {
			response.setStatus(Response.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(e.getMessage());
		}catch (Exception e) {
			response.setStatus(Response.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(e.getMessage());
		}
		
	}
	
	protected String mapToJson(List<Map> map) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(map);
	}

	protected String objectToJson(Object entity) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(entity);
	}
	
	protected Map<String, String> jsonToMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;
        try {
            // convert JSON string to Map
            map = mapper.readValue(json, Map.class);
			// it works
            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
	}

	protected String getBody(HttpServletRequest request) throws Exception {
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
	    
	    return buffer.toString();
	}

	public Map<String,Object> p(HttpServletRequest request, HttpServletResponse response, String method) throws MethodNotFoundException, PathParamNotFoundException, QueryParamNotFoundException, ShowSimpleTextException {
		Node n = null;
		NodeMethodOpenApiBean nodeMethod = null;
		try {
			n = validatePath(request);
			Node n2 = n.get(method);
			
			if(n2 == null && "get".equals(method) && n instanceof NodeRootOpenApiBean) {
				NodeRootOpenApiBean nR = (NodeRootOpenApiBean) n;
				StringBuffer msg = new StringBuffer();
				for(Node item : nR.get()) {
					msg.append(item.getPath()).append("\n");
				}
				throw new ShowSimpleTextException(msg);
			}

			if(n2 == null && "get".equals(method) && n instanceof NodeServiceOpenApiBean) {
				NodeServiceOpenApiBean nR = (NodeServiceOpenApiBean) n;
				StringBuffer msg = new StringBuffer();
				for(Node s : nR.get()) {
					msg.append(s.getPath()).append("\n");
					msg.append(print(s));
					msg.append("\n").append("-----------------").append("\n");
				}
				throw new ShowSimpleTextException(msg);
			}
			
			if(n2 == null && "get".equals(method) && n instanceof NodeVersionOpenApiBean) {
				NodeVersionOpenApiBean nv = (NodeVersionOpenApiBean) n;
				StringBuffer msg = new StringBuffer(nv.toYaml());
				throw new ShowSimpleTextException(msg);
			}
			
			nodeMethod = (NodeMethodOpenApiBean) n2;
		} catch (NullPointerException e) {
			throw new MethodNotFoundException(e);
		} catch (ClassCastException e) {
			throw new MethodNotFoundException(e);
		}
		
		Map<String,Object> pathParam = new HashMap<String, Object>();;
		try {
			if(nodeMethod.havePathParam()) {
				pathParam = validatePathParam(request,nodeMethod);			
			}
		} catch (Exception e) {
			throw new PathParamNotFoundException(e);
		}
		
		Map<String,Object> queryParam = new HashMap<String, Object>();;
		try {
			if(nodeMethod.haveQueryParam()) {
				queryParam = validateQueryParam(request,nodeMethod);
			}
		} catch (Exception e) {
			throw new QueryParamNotFoundException(e);
		}
		
		System.out.println("## --------------------------------");
		System.out.println("## path: " + nodeMethod.getPath());
		System.out.println("## pathParam: " + pathParam);
		System.out.println("## queryParam: " + queryParam);
		
		Map<String,Object> rMap = new HashMap<String, Object>();
		rMap.put("nodeMethod", nodeMethod);
		rMap.put("pathParam", pathParam);
		rMap.put("queryParam", queryParam);
		return rMap;
	}

	private String print(Node v) {
		StringBuffer buffer = new StringBuffer();
		if(v instanceof NodeMethodOpenApiBean) {
			return v.getPath();
		}else {
			for(Node i : v.get()) {
//				buffer.append(print(i)).append("\n");
				String s = print(i);
				if(s != null && !s.isEmpty()) {
					buffer.append(s).append("\n");
				}
			}
		}
		return buffer.toString().trim();
	}

	private Node validatePath(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();
		if(pathInfo != null) {
			String[] uri = request.getPathInfo().split("/");
			Node node = root.get(uri[1]);
			for (int i = 2; i < uri.length; i++) {
				node = node.get(uri[i]);
			}
			return node;			
		}else {
			return root;
		}
	}
	
	private void validateRequestBody(HttpServletRequest request, NodeMethodOpenApiBean nodeMethod) {
		// TODO Auto-generated method stub
	}

	private Map<String,Object> validateQueryParam(HttpServletRequest request, NodeMethodOpenApiBean nodeMethod) throws Exception {
		Map<String,Object> res = new HashMap<String, Object>();
		List parameters = nodeMethod.getParameters();
		for (int i = 0; i < parameters.size(); i++) {
			NodeMap pMap = new NodeMap((Map)parameters.get(i));
			if("query".equals(pMap.get("in").str())) {
				String name = pMap.get("name").str();
				NodeMap schema = pMap.get("schema");
				String p = request.getParameter(name);
				if(p == null) {
					if("true".equals(pMap.get("required").str())) {
						throw new Exception("Falta el siguiente QueryParam: " + name);					
					}
				}else {
					validateDataType(schema, name, p, "No corresponde el tipo de dato para QueryParam: %s(%s) = %s");
					res.put(name, p);
				}				
			}
		}
		return res;
	}

	private Map<String,Object> validatePathParam(HttpServletRequest request, NodeMethodOpenApiBean nodeMethod) throws Exception {
		Map<String,Object> res = new HashMap<String, Object>();
		String[] path = request.getPathInfo().split("/");
		Node node = nodeMethod.getParent();
		List parameters = null;
		for (int i = path.length-1; i >= 0; i--) {
			if(node != null){
				if(node instanceof NodePathOpenApiBean) {
					NodePathOpenApiBean nodePath = (NodePathOpenApiBean)node;
					if(nodePath.isPathParam()) {
						if(parameters == null) {
							parameters = nodePath.getParameters();
						}
						for (int j = parameters.size()-1; j >= 0; j--) {
							NodeMap pMap = new NodeMap((Map)parameters.get(j));
							if("path".equals(pMap.get("in").str())) {
								String name = pMap.get("name").str();
								if(nodePath.getName().equals("{"+name+"}")) {
									String pathValue = path[i];
									NodeMap schema = pMap.get("schema");
									schema = schema.r() == null ? pMap : schema;
									Object v = validateDataType(schema,name,pathValue,"No corresponde el tipo de dato para PathParam: %s(%s) = %s");
									res.put(name, v);
								}
							}
						}
					}
				}
				node = node.getParent();
			}
		}
		return res;
	}
	
	private Object validateDataType(NodeMap schema, String name, String value, String errorMsg) throws Exception {
		NodeMap nType = schema.get("type");
		String type = nType.r() == null ? "string" : nType.str();
		if("array".equals(type)) {
			type = schema.get("items").get("type").str();
		}
		boolean isNumeric = isNumeric(value);
		if(isNumeric == false && ("number".equals(type) || "integer".equals(type))) {
			throw new Exception(String.format(errorMsg,name,type,value));
		}
		
		return isNumeric ?  new Integer(value) : value;
	}
	
	public static boolean isNumeric(String strNum) {
	    return strNum.matches("-?\\d+(\\.\\d+)?");
	}
	
	public abstract void exec(NodeMethodOpenApiBean nodeMethod, Map<String,Object> pathParam, Map<String,Object> queryParam, RepositoryBean repository) throws Exception;
}

