package com.fms.validator.model;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public enum LegalEntity {

    CSZURICH("CS Zurich");

    private String name;

     LegalEntity(String name){
        this.name = name;
    }

     LegalEntity(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
