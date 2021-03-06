package com.fms.validator.factory;

import com.fms.validator.service.Validator;
import com.fms.validator.service.impl.DataValidator;
import com.fms.validator.service.impl.JsonValidator;
import com.fms.validator.service.impl.TypeValidator;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class ServiceFactory {

    private static Validator dataValidator = new DataValidator();

    private static Validator typeValidator = new TypeValidator();

    private static Validator jsonValidator = new JsonValidator();

    private static Validator[] validators;

    private static ServiceFactory serviceFactory = new ServiceFactory();

    private ServiceFactory(){}


    public Validator[] getValidators(){
        if(validators==null) {
            validators = new Validator[3];
            validators[0] = jsonValidator;
            validators[1] = typeValidator;
            validators[2] = dataValidator;
        }
        return validators;
    }

    public static ServiceFactory getInstance(){
        return serviceFactory;
    }
}
