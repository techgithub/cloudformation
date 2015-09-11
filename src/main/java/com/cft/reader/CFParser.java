package com.cft.reader;

import java.io.File;

import com.cft.domain.Node;
import com.cft.domain.NodeGroup;
import com.cft.metadata.DependencyMeta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class CFParser {

	private DependencyMeta meta = new DependencyMeta();	
	
	public NodeGroup parser(String cfTemplate)
	{
		NodeGroup group = new NodeGroup();
		
		try{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(new File(cfTemplate));

			root.get("Resources").fields().forEachRemaining(e ->
			{				
				String rsrcId = e.getKey();
				Node node = new Node(rsrcId);
				
				scanNodeObject(group, e.getValue(), rsrcId, node);
				group.addNode(rsrcId, node);
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return group;
	}

	private void scanNodeObject(NodeGroup group, JsonNode jNode, String rsrcId, Node node) 
	{
		jNode.fields().forEachRemaining(k->{
			
			String dependency = null;
			
			if(k.getKey().equals("DependsOn")){
				dependency = k.getValue().asText();
				group.addDependency(rsrcId, dependency);
			}
			else if(k.getKey().equals("Type")){
				node.setType(k.getValue().asText());
			}
			else if(k.getKey().equals("Properties")){
				if(k.getValue().getNodeType()==JsonNodeType.OBJECT){
					scanWithinProperties(group, k.getValue(), rsrcId, node);
				}
			}
			else{
				Node.Property property = new Node.Property(k.getKey(), k.getValue().toString());
				node.addProperty(property);
			}
		});
	}

	private void scanWithinProperties(NodeGroup group, JsonNode jNode, String rsrcId, Node node) 
	{		
		jNode.fields().forEachRemaining((e)->
		{
			String propertyName = e.getKey();
			if(meta.isDependencyField(propertyName, node.getType())){
				e.getValue().fields().forEachRemaining((k)->{
					if(k.getKey().equalsIgnoreCase("Ref")){
						group.addDependency(rsrcId, k.getValue().asText()); //Adding resource dependency
					}
				});
			}
			else{
				Node.Property property = new Node.Property(propertyName, e.getValue().toString());
				node.addProperty(property);
			}
		});
	}

	public static void main(String[] args) {
		CFParser parser = new CFParser();
		NodeGroup group = parser.parser(CFParser.class.getClassLoader().getResource("sample/cftemplate.json").getFile());
		group.getDependencies().forEach((k,v)->{
			System.out.println("Node: "+k);
			System.out.println("Dependend On: "+v+"\n");
		});
		
		group.getNodeInfoMap().values().forEach(System.out::println);
	}

}
