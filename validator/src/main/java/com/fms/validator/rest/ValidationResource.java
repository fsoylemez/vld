package com.fms.validator.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fms.validator.factory.ServiceFactory;
import com.fms.validator.model.TradeModel;
import com.fms.validator.model.ValidationFault;
import com.fms.validator.model.ValidationResponse;
import com.fms.validator.model.ValidationResult;
import com.fms.validator.service.Validator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
@Path("/validation")
public class ValidationResource {

    @Path("validate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ValidationResponse validateTradeData(final String trades) {

        ValidationResponse response = new ValidationResponse();

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonParser parser = objectMapper.getFactory().createParser(trades);

            int tradeIndex = 0;
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalStateException("Expected an array");
            }
            while (parser.nextToken() == JsonToken.START_OBJECT) {

                ObjectNode node = objectMapper.readTree(parser);
                try {
                    TradeModel tradeModel = objectMapper.treeToValue(node, TradeModel.class);

                    if (tradeModel != null) {
                        for (Validator v : ServiceFactory.getInstance().getValidators()) {
                            v.validate(tradeModel, response, tradeIndex);
                        }
                    } else {
                        ValidationResult result = new ValidationResult();
                        result.addValidationFault(new ValidationFault("Invalid trade data."));
                        result.setDataIndex(tradeIndex);
                        response.addValidationResult(result);
                    }
                } catch (UnrecognizedPropertyException e) {
                    ValidationResult result = new ValidationResult();
                    int index = e.getMessage().indexOf("(");
                    result.addValidationFault(new ValidationFault(e.getMessage().substring(0, index)));
                    result.setDataIndex(tradeIndex);
                    response.addValidationResult(result);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                tradeIndex++;
            }

            parser.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
