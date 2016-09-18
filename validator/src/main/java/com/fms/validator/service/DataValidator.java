package com.fms.validator.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fms.validator.model.TradeModel;
import com.sun.javafx.binding.StringFormatter;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.StringDatatypeValidator;

public class DataValidator {
	
	private static SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private String validateTradeData(TradeModel tradeModel){
		
		
		
		
		
		return "Valid";
	}
	
	private String validateSpotForward(TradeModel tradeModel){
		
		return "Valid";
	}

}
