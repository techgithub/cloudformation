package com.cft.metadata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DependencyMeta 
{
	private Map<String, Set<String>> dependencyMeta;
	
	public DependencyMeta()
	{
		dependencyMeta = new HashMap<String, Set<String>>();
		
		dependencyMeta.put("vpcid", new HashSet<String>(Arrays.asList(new String[]{"subnet","vpcgatewayattachment","routetable","networkac1","securitygroup"})));
		dependencyMeta.put("dependson", new HashSet<String>(Arrays.asList(new String[]{"route","eip","instance"})));
		dependencyMeta.put("subnetid", new HashSet<String>(Arrays.asList(new String[]{"subnetroutetableassociation","subnetnetworkaclassociation"})));
		dependencyMeta.put("networkaclid", new HashSet<String>(Arrays.asList(new String[]{"networkaclentry","networkaclentry"})));
	}
	
	public boolean isDependencyField(String propKey, String nodeType)
	{
		try{
			String type = nodeType.substring(nodeType.lastIndexOf(":")+1);
			return dependencyMeta.get(propKey.toLowerCase()).contains(type.toLowerCase());
		}catch(Exception ex){
			return false;
		}
	}
	
}
