package com.fms.validator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by fatih.soylemez on 9/20/2016.
 */
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isValid(String json){
        try{

            JsonNode jsonNode = objectMapper.readTree(json);

        } catch(JsonProcessingException e){
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
