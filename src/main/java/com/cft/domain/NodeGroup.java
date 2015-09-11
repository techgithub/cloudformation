package com.cft.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class NodeGroup 
{
	private Map<String, Node> nodeIdObjMap;
	
	private Map<String, String> dependencies;
	
	public NodeGroup(){
		nodeIdObjMap = new LinkedHashMap<String, Node>();
		dependencies = new HashMap<String, String>();
	}
	
	public void addDependency(String src, String trgt){
		dependencies.put(src, trgt);
	}
	
	public void addNode(String id, Node node){
		nodeIdObjMap.put(id, node);
	}

	public Map<String, Node> getNodeInfoMap() {
		return nodeIdObjMap;
	}

	public Map<String, String> getDependencies() {
		return dependencies;
	}
}
