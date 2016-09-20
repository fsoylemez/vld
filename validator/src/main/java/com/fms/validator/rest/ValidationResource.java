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
import com.fms.validator.util.JsonUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
@Path("/validation")
public class ValidationResource {

    private static boolean shutFlag = false;

    @Path("shutdown")
    @GET
    public Response validateTradeData() {
        shutFlag = true;
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @Path("validate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ValidationResponse validateTradeData(final String trades) {

        if(!shutFlag) {

            ValidationResponse response = new ValidationResponse();

            //validate json string
            if (!JsonUtils.isValid(trades)) {
                response.addValidationResult(createValidationResult("Invalid json.", 0));
                return response;
            }

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonParser parser = objectMapper.getFactory().createParser(trades);

                int tradeIndex = 0;
                JsonToken token = parser.nextToken();
                if (token == JsonToken.START_OBJECT) {
                    doValidation(objectMapper, parser, response, tradeIndex);
                } else {
                    while (parser.nextToken() == JsonToken.START_OBJECT) {

                        doValidation(objectMapper, parser, response, tradeIndex);

                        tradeIndex++;
                    }
                }
                parser.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
     //       return Response.status(500).entity(response).build();
        return null;
    }

    public void doValidation(ObjectMapper objectMapper,JsonParser parser,ValidationResponse response,int tradeIndex){
        try {
            ObjectNode node = objectMapper.readTree(parser);

            TradeModel tradeModel = objectMapper.treeToValue(node, TradeModel.class);

            if (tradeModel != null) {
                for (Validator v : ServiceFactory.getInstance().getValidators()) {
                    v.validate(tradeModel, response, tradeIndex);
                }
            } else {
                response.addValidationResult(createValidationResult("Invalid trade data.", tradeIndex));
            }
        } catch (UnrecognizedPropertyException e) {
            int index = e.getMessage().indexOf("(");
            response.addValidationResult(createValidationResult(e.getMessage().substring(0, index), tradeIndex));
        } catch (JsonProcessingException e) {
            int index = e.getMessage().indexOf(")");
            response.addValidationResult(createValidationResult(e.getMessage().substring(0, index), tradeIndex));
        } catch (IOException e){
            response.addValidationResult(createValidationResult(e.getMessage(), tradeIndex));
        }
    }

    public ValidationResult createValidationResult(String message,int tradeIndex){
        ValidationResult result = new ValidationResult();
        result.addValidationFault(new ValidationFault(message));
        result.setDataIndex(tradeIndex);
        return result;
    }
}
