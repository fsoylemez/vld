package com.fms.validator.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.validator.model.TradeModel;
import com.fms.validator.model.ValidationResponse;
import com.fms.validator.service.Validator;
import com.fms.validator.service.impl.TypeValidator;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class TypeFunctionalTest {

    @Test
    public void checkDouble(){
        try {
            File resourcesDirectory = new File("src/test/resources/exampleData/tradeData_invalidDouble.json");

            //read json file data to String
            byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            TradeModel[] tradeModels = null;

            JsonNode jsonNode = objectMapper.readTree(jsonData);

            TradeModel tradeModel = objectMapper.treeToValue(jsonNode,TradeModel.class);

            ValidationResponse response = new ValidationResponse();
            Validator validator = new TypeValidator();

            validator.validate(tradeModel,response,0);

            Assert.assertEquals(response.getValidationResults().size(),1);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkDate(){
        try {
            File resourcesDirectory = new File("src/test/resources/exampleData/tradeData_invalidDate.json");

            //read json file data to String
            byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            TradeModel[] tradeModels = null;

            JsonNode jsonNode = objectMapper.readTree(jsonData);

            TradeModel tradeModel = objectMapper.treeToValue(jsonNode,TradeModel.class);

            ValidationResponse response = new ValidationResponse();
            Validator validator = new TypeValidator();

            validator.validate(tradeModel,response,0);

            Assert.assertEquals(response.getValidationResults().size(),1);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
