package org.microcks.model.node.swagger;

import org.microcks.model.NodeMap;
import org.microcks.model.node.Node;

public class NodeServiceSwaggerBean extends Node {

	public NodeServiceSwaggerBean(Node parent, NodeMap metadata, String swagger) {
		NodeMap info = metadata.get("info");
		String name = info.get("title").str();
		String version = info.get("version").str();
		//-----------------------------------------------------------
		this.parent = parent;
		this.name = name;
		Node nodeVersion = new NodeVersionSwaggerBean(this,version,metadata,swagger);
		add(nodeVersion);
	}
	
}