package com.fms.validator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class ValidationResult {

    private ValidationStatus responseStatus;

    private int tradeIndex;

    private List<ValidationFault> validationFaults;

    public ValidationResult(ValidationStatus responseStatus, List<ValidationFault> validationFaults){
        this.responseStatus = responseStatus;
        this.validationFaults = validationFaults;
    }

    public ValidationResult(){}

    public ValidationStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ValidationStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<ValidationFault> getValidationFaults() {
        if(validationFaults==null)
            validationFaults = new ArrayList<ValidationFault>();
        return validationFaults;
    }

    public void setValidationFaults(List<ValidationFault> validationFaults) {
        this.validationFaults = validationFaults;
    }

    public void addValidationFault(ValidationFault fault){
        getValidationFaults().add(fault);
    }

    public int getTradeIndex() {
        return tradeIndex;
    }

    public void setTradeIndex(int tradeIndex) {
        this.tradeIndex = tradeIndex;
    }
}
