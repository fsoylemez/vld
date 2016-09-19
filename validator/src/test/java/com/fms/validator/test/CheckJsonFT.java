package com.fms.validator.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.validator.model.TradeModel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class CheckJsonFT {

    public static void main(String[] args) {
        File resourcesDirectory = new File("src/test/resources/exampleData/tradeData1.json");

        System.out.println(resourcesDirectory.getAbsolutePath());

        try {
            //read json file data to String
            byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();



            JsonNode jsonNode = objectMapper.readTree(jsonData);
            if (jsonNode.isArray()) {
                TradeModel[] newFoo = objectMapper.treeToValue(jsonNode,TradeModel[].class);
            } else {
                TradeModel newFoo = objectMapper.treeToValue(jsonNode,TradeModel.class);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
