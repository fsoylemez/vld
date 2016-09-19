package com.fms.validator.service.impl;

import com.fms.validator.model.TradeModel;
import com.fms.validator.model.ValidationFault;
import com.fms.validator.model.ValidationResponse;
import com.fms.validator.model.ValidationResult;
import com.fms.validator.service.Validator;
import com.fms.validator.util.DateUtils;


public class TypeValidator implements Validator {


    public void validate(TradeModel tradeModel, ValidationResponse response,int tradeIndex) {
        ValidationResult validationResult = new ValidationResult();

        if(tradeModel.getAmount1()!=null && !tradeModel.getAmount1().equals("")){
            try{
                Double.parseDouble(tradeModel.getAmount1());
            }catch(NumberFormatException e){
                validationResult.addValidationFault(new ValidationFault("Invalid amount1 value."));

            }
        }
        if(tradeModel.getAmount2()!=null && !tradeModel.getAmount2().equals("")){
            try{
                Double.parseDouble(tradeModel.getAmount2());
            }catch(NumberFormatException e){
                validationResult.addValidationFault(new ValidationFault("Invalid amount2 value."));

            }
        }

        if(tradeModel.getRate()!=null && !tradeModel.getRate().equals("")){
            try{
                Double.parseDouble(tradeModel.getRate());
            }catch(NumberFormatException e){
                validationResult.addValidationFault(new ValidationFault("Invalid rate value."));

            }
        }

        if(tradeModel.getValueDate()!=null && !tradeModel.getValueDate().equals("") && DateUtils.toDate(tradeModel.getValueDate())==null){
            validationResult.addValidationFault(new ValidationFault("ValueDate can not be parsed."));
        }

        if(tradeModel.getExcerciseStartDate()!=null && !tradeModel.getExcerciseStartDate().equals("") && DateUtils.toDate(tradeModel.getExcerciseStartDate())==null){
            validationResult.addValidationFault(new ValidationFault("excercise date can not be parsed."));
        }

        if(tradeModel.getTradeDate()!=null && !tradeModel.getTradeDate().equals("") && DateUtils.toDate(tradeModel.getTradeDate())==null){
            validationResult.addValidationFault(new ValidationFault("trade date can not be parsed."));
        }

        if(tradeModel.getExpiryDate()!=null && !tradeModel.getExpiryDate().equals("") && DateUtils.toDate(tradeModel.getExpiryDate())==null){
            validationResult.addValidationFault(new ValidationFault("expiry date can not be parsed."));
        }

        if(tradeModel.getPremiumDate()!=null && !tradeModel.getPremiumDate().equals("") && DateUtils.toDate(tradeModel.getPremiumDate())==null){
            validationResult.addValidationFault(new ValidationFault("premiumDate can not be parsed."));
        }

        if(tradeModel.getDeliveryDate()!=null && !tradeModel.getDeliveryDate().equals("") && DateUtils.toDate(tradeModel.getDeliveryDate())==null){
            validationResult.addValidationFault(new ValidationFault("deliveryDate can not be parsed."));
        }

        validationResult.setDataIndex(tradeIndex);

        if(validationResult.getValidationFaults().size()>0){
            response.addValidationResult(validationResult);
        }
    }
}
