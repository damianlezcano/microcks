package org.microcks.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public NodeMap lookup(String key) {
		NodeMap np = null;
		if(r instanceof Map) {
			for (Object item : ((Map) r).entrySet()) {
				Entry entry = (Entry)item;
				if(entry.getKey().equals(key)) {
					return new NodeMap(entry.getValue());
				}else{
					np = new NodeMap(entry.getValue()).lookup(key);
					if(np != null) {
						return np;
					}
				}
			}
		}
		return np;
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
