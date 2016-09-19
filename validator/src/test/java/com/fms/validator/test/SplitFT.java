package com.fms.validator.test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fms.validator.model.*;
import com.fms.validator.service.DataValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SplitFT {

	static DataValidator dataValidator = new DataValidator();

	public static void main(String[] args) throws IOException {
		ValidationResponse response = new ValidationResponse();
		
		File resourcesDirectory = new File("src/test/resources/exampleData/tradeData1.json");
		
		System.out.println(resourcesDirectory.getAbsolutePath());
		
		//read json file data to String
		byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

		ObjectMapper objectMapper = new ObjectMapper();
		JsonParser parser = objectMapper.getFactory().createParser(jsonData);

		int tradeIndex = 0;
		if(parser.nextToken() != JsonToken.START_ARRAY) {
		  throw new IllegalStateException("Expected an array");
		}
		while(parser.nextToken() == JsonToken.START_OBJECT) {
		  // read everything from this START_OBJECT to the matching END_OBJECT
		  // and return it as a tree model ObjectNode
		  ObjectNode node = objectMapper.readTree(parser);
			try {
				TradeModel tradeModel = objectMapper.treeToValue(node, TradeModel.class);
				ValidationResult result = dataValidator.validate(tradeModel);
				result.setTradeIndex(tradeIndex);
				if (result.getResponseStatus() == ValidationStatus.FAIL) {
					response.addValidationResult(result);
				}
			}catch(UnrecognizedPropertyException e){
				ValidationResult result = new ValidationResult();
				int index = e.getMessage().indexOf("(");
				result.addValidationFault(new ValidationFault(e.getMessage().substring(0,index)));
				result.setResponseStatus(ValidationStatus.FAIL);
				response.addValidationResult(result);
			}

			tradeIndex++;
		}
		System.out.println(response.getValidationResults().size());

		parser.close();

	}

}
