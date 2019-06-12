package org.microcks.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.microcks.exception.DuplicateEntityException;

public class RepositoryBean {
	
	private Map<String,List<Map>> rep = new HashMap<String, List<Map>>();

	private List<Map> list = new ArrayList<Map>();
	
	public List<Map> get(Map<String, Object> pathParam, Map<String, Object> queryParam) {
		
		List<Map> nList1 = new ArrayList();
		
		if(pathParam != null && !pathParam.isEmpty()) {
			for(Map item : list) {
				boolean f = true;
				for (Entry entry : pathParam.entrySet()) {
					if(item.containsKey(entry.getKey())) {
						if(!entry.getValue().equals(item.get(entry.getKey()))){
							f = false;
							break;
						}
					}else {
						f = false;
					}
				}
				if(f) {
					nList1.add(item);
				}
			}
		}else{
			nList1.addAll(list);
		}
		
		List<Map> nList2 = new ArrayList();
		
		for(Map item : nList1) {
			boolean f = true;
			for (Entry entry : queryParam.entrySet()) {
				if(item.containsKey(entry.getKey())) {
					if(!entry.getValue().equals(item.get(entry.getKey()))){
						f = false;
						break;
					}
				}
			}
			if(f) {
				nList2.add(item);
			}
		}
		
		return nList2;
	}

	public void post(Map<String, String> map, Map<String, Object> pathParam, Map<String, Object> queryParam) throws DuplicateEntityException {
		List<Map> nList1 = get(pathParam, queryParam);
		if(nList1.isEmpty()) {
			list.add(map);			
		}else {
			throw new DuplicateEntityException("La entidad " + map + " ya existe");
		}
	}

	public void delete(Map<String, Object> pathParam, Map<String, Object> queryParam) {
		List<Map> nList1 = get(pathParam, queryParam);
		for (Map item : nList1) {
			list.remove(item);
		}
	}

	public void put(Map<String, String> map, Map<String, Object> pathParam, Map<String, Object> queryParam) {
		List<Map> nList1 = get(pathParam, queryParam);
		for (Map item : nList1) {
			item.putAll(map);
		}
	}
	
}