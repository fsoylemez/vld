package com.fms.validator.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.validator.model.TradeModel;
import com.fms.validator.service.DataValidator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class CounterPartyFT {

    public static void main(String[] args){
        File resourcesDirectory = new File("src/test/resources/exampleData/tradeData1.json");

        System.out.println(resourcesDirectory.getAbsolutePath());

        try {
            //read json file data to String
            byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            TradeModel[] tradeModels = null;

            JsonNode jsonNode = objectMapper.readTree(jsonData);
            if (jsonNode.isArray()) {
                 tradeModels = objectMapper.treeToValue(jsonNode,TradeModel[].class);
            } else {
                TradeModel tradeModel = objectMapper.treeToValue(jsonNode,TradeModel.class);
            }

            DataValidator dv = new DataValidator();
            System.out.println(dv.validate(tradeModels[0]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
