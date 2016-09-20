package com.fms.validator.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fms.validator.model.TradeModel;
import com.fms.validator.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class JsonFunctionalTest {

    @Test
    public void checkValidJson() {
        try {
            File resourcesDirectory = new File("src/test/resources/exampleData/tradeData_invalid.json");

            //read json file data to String
            byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

            Assert.assertFalse(JsonUtils.isValid(new String(jsonData)));

        } catch (NoSuchFileException e) {
            Assert.fail("Test file not found.");
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void checkValidObject() {
        try {
            File resourcesDirectory = new File("src/test/resources/exampleData/tradeData_invalidObject.json");

            //read json file data to String
            byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

            ObjectMapper objectMapper = new ObjectMapper();

            TradeModel trade = objectMapper.readValue(jsonData, TradeModel.class);

            Assert.fail("UnrecognizedPropertyException expected");

        }
        catch(UnrecognizedPropertyException e){
            //Ok got the exception
        }
        catch (NoSuchFileException e) {
            Assert.fail("Test file not found.");
        } catch (IOException e) {

        }
    }

    @Test
    public void checkSingleObject() {

        try {
            File resourcesDirectory = new File("src/test/resources/exampleData/tradeData_single.json");

            //read json file data to String
            byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

            ObjectMapper objectMapper = new ObjectMapper();

            TradeModel trade = objectMapper.readValue(jsonData, TradeModel.class);

            Assert.assertNotNull(trade);

        } catch (NoSuchFileException e) {
            Assert.fail("Test file not found.");
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void checkArray() {

        try {
            File resourcesDirectory = new File("src/test/resources/exampleData/tradeData_multiple.json");

            //read json file data to String
            byte[] jsonData = Files.readAllBytes(Paths.get(resourcesDirectory.getAbsolutePath()));

            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            TradeModel[] trades = objectMapper.readValue(jsonData, TradeModel[].class);

            Assert.assertNotNull(trades);

        } catch (NoSuchFileException e) {
            Assert.fail("Test file not found.");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
