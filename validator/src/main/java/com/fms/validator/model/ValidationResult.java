package com.fms.validator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class ValidationResult {

    private int dataIndex;

    private List<ValidationFault> validationFaults;

    public ValidationResult(ValidationStatus responseStatus, List<ValidationFault> validationFaults){
        this.validationFaults = validationFaults;
    }

    public ValidationResult(){}

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

    public int getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(int dataIndex) {
        this.dataIndex = dataIndex;
    }
}
