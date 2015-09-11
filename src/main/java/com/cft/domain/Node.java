package com.cft.domain;

import java.util.ArrayList;
import java.util.List;

public class Node 
{
	private String id;
	private String type;	
	private List<Property> properties;
	
	public Node(String id){
		this.id = id;
		properties = new ArrayList<Node.Property>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Id: ").append(id).append(",\n")
		.append("Type: ").append(type).append(",\n")
		.append("Properties: ").append(properties);
		
		return builder.toString();
	}
	
	
	public static class Property
	{
		private String name;
		private String value;
		
		public Property(String name, String value){
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() 
		{
			StringBuilder builder = new StringBuilder();
			builder.append("[Name: ").append(name).append(", ").append("Value: ").append(value).append("]");
			return builder.toString();
		}
	}

	public void addProperty(Property property) 
	{
		properties.add(property);
	}
}

