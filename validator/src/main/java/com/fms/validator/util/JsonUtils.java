package com.fms.validator.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	static ObjectMapper objectMapper = new ObjectMapper();


	public static boolean isValidJSON(final String json) throws IOException {
	    boolean valid = true;
	    try{ 
	        objectMapper.readTree(json);
	    } catch(JsonProcessingException e){
	        valid = false;
	    }
	    return valid;
	}
	
}
