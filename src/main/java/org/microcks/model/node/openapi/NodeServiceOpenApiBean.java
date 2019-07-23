package org.microcks.model.node.openapi;

import org.microcks.model.NodeMap;
import org.microcks.model.node.Node;

public class NodeServiceOpenApiBean extends Node {

	public NodeServiceOpenApiBean(Node parent, NodeMap metadata, String yaml) {
		NodeMap info = metadata.get("info");
		String name = info.get("title").str();
		String version = info.get("version").str();
		//-----------------------------------------------------------
		this.parent = parent;
		this.name = name;
		Node nodeVersion = new NodeVersionOpenApiBean(this,version,metadata,yaml);
		add(nodeVersion);
	}
	
}