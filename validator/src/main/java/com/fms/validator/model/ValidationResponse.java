package com.fms.validator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class ValidationResponse {

    private List<ValidationResult> validationResults;

    public ValidationResponse(List<ValidationResult> validationResults){
        this.validationResults = validationResults;
    }

    public ValidationResponse(){}

    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }

    public void setValidationResults(List<ValidationResult> validationResults) {
        this.validationResults = validationResults;
    }

    public void addValidationResult(ValidationResult result){
        if(validationResults==null)
            validationResults = new ArrayList<ValidationResult>();

        validationResults.add(result);
    }
}
