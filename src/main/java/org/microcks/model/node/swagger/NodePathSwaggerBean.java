package org.microcks.model.node.swagger;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.microcks.model.node.Node;

public class NodePathSwaggerBean extends Node {

	private List parameters;
	
	public NodePathSwaggerBean(Node parent, String key) {
		this.parent = parent;
		this.name = key;
	}

	public NodePathSwaggerBean(Node parent, String name, Map map) {
		this(parent,name);
		for (Object obj : map.entrySet()) {
			Entry entry = (Entry) obj;
			if("get".equals(entry.getKey())) {
				add(new NodeMethodSwaggerBean(this,entry));
			}else if("post".equals(entry.getKey())) {
				add(new NodeMethodSwaggerBean(this,entry));
			}else if("put".equals(entry.getKey())) {
				add(new NodeMethodSwaggerBean(this,entry));
			}else if("delete".equals(entry.getKey())) {
				add(new NodeMethodSwaggerBean(this,entry));
			}else if("parameters".equals(entry.getKey())) {
				parameters = buildParameters((List) entry.getValue());
			}
		}
	}
	
	private List buildParameters(List map) {
		return map;
	}

	public Node get(String value) {
		Node r = super.get(value);
		if(r == null) {
			for (Node node : nodes) {
				if(node.getName().startsWith("{")) {
					r = node;
					break;
				}
			}
		}
		return r;
	}

	public List getParameters() {
		return parameters;
	}
	
	public boolean isPathParam() {
		return name.startsWith("{") && name.endsWith("}");
	}
	
}