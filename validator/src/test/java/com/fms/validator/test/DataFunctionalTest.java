package com.fms.validator.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.validator.model.TradeModel;
import com.fms.validator.model.ValidationResponse;
import com.fms.validator.service.Validator;
import com.fms.validator.service.impl.DataValidator;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataFunctionalTest {

	@Test
	public void checkCurrency(){
		try {
			File resourcesDirectory = new File("src/test/resources/exampleData/tradeData_invalidCurrency.json");

			//read json file data to String
			byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			TradeModel[] tradeModels = null;

			JsonNode jsonNode = objectMapper.readTree(jsonData);

			TradeModel tradeModel = objectMapper.treeToValue(jsonNode,TradeModel.class);

			ValidationResponse response = new ValidationResponse();
			Validator validator = new DataValidator();

			validator.validate(tradeModel,response,0);

			Assert.assertEquals(response.getValidationResults().get(0).getValidationFaults().get(0).getMessage(),"Currency is not in ISO 4217.");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkCounterParty(){
		try {
			File resourcesDirectory = new File("src/test/resources/exampleData/tradeData_invalidCounterParty.json");

			//read json file data to String
			byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

			//create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			TradeModel[] tradeModels = null;

			JsonNode jsonNode = objectMapper.readTree(jsonData);

			TradeModel tradeModel = objectMapper.treeToValue(jsonNode,TradeModel.class);

			ValidationResponse response = new ValidationResponse();
			Validator validator = new DataValidator();

			validator.validate(tradeModel,response,0);

			Assert.assertEquals(response.getValidationResults().size(),1);
			Assert.assertEquals(response.getValidationResults().get(0).getValidationFaults().get(0).getMessage(),"Counterparty is not supported.");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
