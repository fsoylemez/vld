package com.fms.validator.service;

import com.fms.validator.constants.TradeConstans;
import com.fms.validator.model.*;
import com.fms.validator.util.DateUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;

public class DataValidator {
	

	public ValidationResult validate(TradeModel tradeModel){
		ValidationResult validationResult = new ValidationResult();
		if(tradeModel!=null) {
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
				if(tradeModel.getValueDate()!=null && !tradeModel.getValueDate().equals("") && valueDate==null){
					validationResult.addValidationFault(new ValidationFault("ValueDate can not be parsed."));
				}
				//check whether counterparty is valid
				try {
					Customer customer = Customer.valueOf(tradeModel.getCustomer());
					//yes
				} catch (IllegalArgumentException ex) {
					validationResult.addValidationFault(new ValidationFault("Counterparty is not supported."));
				}
				//check whether currency is in iso 4217 currencies
				if (tradeModel.getPayCcy() != null && !tradeModel.getPayCcy().equals("") && !Currency.getAvailableCurrencies().contains(Currency.getInstance(tradeModel.getPayCcy()))) {
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

		}
		else{
			validationResult.addValidationFault(new ValidationFault("Invalid trade data."));
		}
		if(validationResult.getValidationFaults().size()>0){
			validationResult.setResponseStatus(ValidationStatus.FAIL);
		}
		else{
			validationResult.setResponseStatus(ValidationStatus.SUCCESS);

		}

		return validationResult;
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
		if(tradeModel.getExcerciseStartDate()!=null && !tradeModel.getExcerciseStartDate().equals("") && excerciseDate==null){
			validationResult.addValidationFault(new ValidationFault("excercise date can not be parsed."));
		}
		Date tradeDate = DateUtils.toDate(tradeModel.getTradeDate());
		if(tradeModel.getTradeDate()!=null && !tradeModel.getTradeDate().equals("") && tradeDate==null){
			validationResult.addValidationFault(new ValidationFault("trade date can not be parsed."));
		}
		Date expiryDate = DateUtils.toDate(tradeModel.getExpiryDate());
		if(tradeModel.getExpiryDate()!=null && !tradeModel.getExpiryDate().equals("") && expiryDate==null){
			validationResult.addValidationFault(new ValidationFault("expiry date can not be parsed."));
		}

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
		if(tradeModel.getDeliveryDate()!=null && !tradeModel.getDeliveryDate().equals("") && deliveryDate==null){
			validationResult.addValidationFault(new ValidationFault("deliveryDate can not be parsed."));
		}
		Date premiumDate = DateUtils.toDate(tradeModel.getPremiumDate());
		if(tradeModel.getPremiumDate()!=null && !tradeModel.getPremiumDate().equals("") && premiumDate==null){
			validationResult.addValidationFault(new ValidationFault("premiumDate can not be parsed."));
		}
		if(expiryDate!=null && deliveryDate!=null && premiumDate!=null) {
			// validate premium date and expiry date
			if (!(expiryDate.before(deliveryDate) &&
					premiumDate.before(deliveryDate))) {
				validationResult.addValidationFault(new ValidationFault("Expire date and premium date should be  before delivery date."));
			}
		}
	}

}

