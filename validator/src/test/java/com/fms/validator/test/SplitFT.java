package com.fms.validator.test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fms.validator.model.*;
import com.fms.validator.service.Validator;
import com.fms.validator.service.impl.DataValidator;
import com.fms.validator.service.impl.JsonValidator;
import com.fms.validator.service.impl.TypeValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SplitFT {

	public static void main(String[] args) throws IOException {
		ValidationResponse response = new ValidationResponse();

		Validator[] validators = new Validator[3];
		validators[0] = new JsonValidator();
		validators[1] = new TypeValidator();
		validators[2] = new DataValidator();
		
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

		  ObjectNode node = objectMapper.readTree(parser);
			try {
				TradeModel tradeModel = objectMapper.treeToValue(node, TradeModel.class);

				if(tradeModel!=null) {
					for (Validator v : validators) {
						v.validate(tradeModel, response, tradeIndex);
					}
				}
				else{
					ValidationResult result = new ValidationResult();
					result.addValidationFault(new ValidationFault("Invalid trade data."));
					result.setDataIndex(tradeIndex);
					response.addValidationResult(result);
				}
			}catch(UnrecognizedPropertyException e){
				ValidationResult result = new ValidationResult();
				int index = e.getMessage().indexOf("(");
				result.addValidationFault(new ValidationFault(e.getMessage().substring(0,index)));
				result.setDataIndex(tradeIndex);
				response.addValidationResult(result);
			}

			tradeIndex++;
		}
//		System.out.println(response.getValidationResults().size());

		parser.close();

	}

}
