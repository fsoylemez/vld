package com.fms.validator.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class SplitFT {

	public static void main(String[] args) throws IOException {
		
		
		File resourcesDirectory = new File("src/test/resources/exampleData/tradeData1.json");
		
		System.out.println(resourcesDirectory.getAbsolutePath());
		
		//read json file data to String
		byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

		ObjectMapper mapper = new ObjectMapper();
		JsonParser parser = mapper.getFactory().createParser(jsonData);
		if(parser.nextToken() != JsonToken.START_ARRAY) {
		  throw new IllegalStateException("Expected an array");
		}
		while(parser.nextToken() == JsonToken.START_OBJECT) {
		  // read everything from this START_OBJECT to the matching END_OBJECT
		  // and return it as a tree model ObjectNode
		  ObjectNode node = mapper.readTree(parser);
System.out.println(node.toString());
		  // do whatever you need to do with this object
		}

		parser.close();

	}

}
