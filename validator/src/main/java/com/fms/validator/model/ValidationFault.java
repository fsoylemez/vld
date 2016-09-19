package com.fms.validator.model;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class ValidationFault {

    private String message;


    public ValidationFault(String message){
        this.message = message;
    }
    public ValidationFault(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
