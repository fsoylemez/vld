package com.fms.validator.service.impl;

import com.fms.validator.constants.TradeConstans;
import com.fms.validator.model.*;
import com.fms.validator.service.Validator;
import com.fms.validator.util.DateUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;


public class DataValidator implements Validator{
	

	public void validate(TradeModel tradeModel,ValidationResponse response,int tradeIndex){
		ValidationResult validationResult = new ValidationResult();
			try {

				Date valueDate = DateUtils.toDate(tradeModel.getValueDate());
				if(valueDate!=null){

					LocalDate lcd = valueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					//check whether valuedate is in weekend
					if (lcd.getDayOfWeek().equals(DayOfWeek.SATURDAY) || lcd.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
						validationResult.addValidationFault(new ValidationFault("ValueDate can not be in weekend."));
					}
					//check whether valuedate is before tradedate
					if (valueDate.before(DateUtils.toDate(tradeModel.getTradeDate()))) {
						validationResult.addValidationFault(new ValidationFault("ValueDate can not be before tradeDate."));
					}
				}

				//check whether counterparty is valid
				try {
					Customer customer = Customer.valueOf(tradeModel.getCustomer());
					//yes
				} catch (IllegalArgumentException ex) {
					validationResult.addValidationFault(new ValidationFault("Counterparty is not supported."));
				}
				try {
					//check whether currency is in iso 4217 currencies
					if (tradeModel.getPayCcy() != null && !tradeModel.getPayCcy().equals("") && !Currency.getAvailableCurrencies().contains(Currency.getInstance(tradeModel.getPayCcy()))) {
						validationResult.addValidationFault(new ValidationFault("Currency is not in ISO 4217."));
					}
				}catch (IllegalArgumentException e){//can not get currency instance
					validationResult.addValidationFault(new ValidationFault("Currency is not in ISO 4217."));
				}

				//check whether legal entity is valid
				boolean validLegalEntity = false;
				for(LegalEntity le:LegalEntity.values()){
					if(le.getName().equals(tradeModel.getLegalEntity()))
						validLegalEntity = true;
				}
				if(!validLegalEntity){
					validationResult.addValidationFault(new ValidationFault("LegalEntity is not valid."));
				}

				//validate spot
				if (tradeModel.getType().equals("Spot")){
					validateSpot(tradeModel,validationResult);
				}

				//validate forward
				else if(tradeModel.getType().equals("Forward")){
					validateForward(tradeModel,validationResult);
				}
				//validate specific options
				else {
					validateOtherOptions(tradeModel,validationResult);
				}

			} catch (Exception e) {
				validationResult.addValidationFault(new ValidationFault("Invalid trade data.Message:"+e.getMessage()));
			}

		validationResult.setDataIndex(tradeIndex);

		if(validationResult.getValidationFaults().size()>0){
			response.addValidationResult(validationResult);
		}
	//	return validationResult;
	}
	
	private void validateSpot(TradeModel tradeModel,ValidationResult validationResult){

		//Spot value date should be today
			Date valueDate = DateUtils.toDate(tradeModel.getValueDate());
			if(valueDate!=null && !valueDate.equals((DateUtils.toDate(TradeConstans.CURRENT_DATE)))){
				validationResult.addValidationFault(new ValidationFault("Spot date should be current date."));
			}

	}

	private void validateForward(TradeModel tradeModel,ValidationResult validationResult){

		//Forward value date should be after today
			Date valueDate = DateUtils.toDate(tradeModel.getValueDate());
			if(valueDate!=null && !valueDate.after((DateUtils.toDate(TradeConstans.CURRENT_DATE)))){
				validationResult.addValidationFault(new ValidationFault("Forward date should be after current date."));
			}

	}

	private void validateOtherOptions(TradeModel tradeModel,ValidationResult validationResult){
		//check whether style is valid
		try {
			Style style = Style.valueOf(tradeModel.getStyle());
			//yes
		} catch (IllegalArgumentException ex) {
			validationResult.addValidationFault(new ValidationFault("Style is not supported."));
		}

		Date excerciseDate = DateUtils.toDate(tradeModel.getExcerciseStartDate());
		Date tradeDate = DateUtils.toDate(tradeModel.getTradeDate());
		Date expiryDate = DateUtils.toDate(tradeModel.getExpiryDate());

		//validate excerciseDate against type
		if(tradeModel.getType().equals(Style.AMERICAN.toString())){

			if(excerciseDate!=null && tradeDate!=null && expiryDate!=null) {
				 if (!(excerciseDate.after(tradeDate) &&
						excerciseDate.before(expiryDate))) {
					validationResult.addValidationFault(new ValidationFault("Excercise date should be after trade date and before expiry date."));
				}
			}
		}
		Date deliveryDate = DateUtils.toDate(tradeModel.getDeliveryDate());
		Date premiumDate = DateUtils.toDate(tradeModel.getPremiumDate());

		if(expiryDate!=null && deliveryDate!=null && premiumDate!=null) {
			// validate premium date and expiry date
			if (!(expiryDate.before(deliveryDate) &&
					premiumDate.before(deliveryDate))) {
				validationResult.addValidationFault(new ValidationFault("Expire date and premium date should be  before delivery date."));
			}
		}
	}

}

