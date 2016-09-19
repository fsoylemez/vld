package com.fms.validator.service;

import com.fms.validator.model.TradeModel;
import com.fms.validator.model.ValidationResponse;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public interface Validator {

    void validate(TradeModel tradeModel, ValidationResponse response,int tradeIndex);

}
