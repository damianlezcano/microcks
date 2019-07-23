package org.microcks.model;

import java.util.List;
import java.util.Map;

public class NodeMap {

	private Object r;
	
	public NodeMap(Object map) {
		this.r = map;
	}

	public NodeMap get(Object key) {
		if(r instanceof Map) {
			Map m = (Map) r;
			Object o = m.get(key);
			return new NodeMap(o);
		}else {
			return new NodeMap(r);
		}
	}
	
	public List list() {
		return (List) r;
	}
	
	public Object r() {
		return r;
	}
	
	public Map map() {
		return (Map)r;
	}

	public String str() {
		return r.toString();
	}
	
}
