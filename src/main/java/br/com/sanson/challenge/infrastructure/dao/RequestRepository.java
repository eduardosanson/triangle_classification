package br.com.sanson.challenge.infrastructure.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class RequestRepository {

    private DynamoDB dynamoDB;
    private Gson gson;

    private static final String TABLENAME = "request";
    private static final String PRIMARY_KEY = "requestKey";

    public RequestRepository(Gson gson, DynamoDB dynamoDB) {
        this.gson = gson;
        this.dynamoDB = dynamoDB;
    }

    public void saveRequest(Map request, Map response, Context context){
        LambdaLogger logger = context.getLogger();
        try{
            var item = new Item();
            item.withMap("request", request);
            item.withMap("response", response);
            dynamoDB.getTable(TABLENAME)
                    .putItem(item
                            .with(PRIMARY_KEY, context.getAwsRequestId()));
        }catch (Exception ex){
            ex.printStackTrace();
            logger.log("ERROR try to save history " + ex.getMessage());
        }


    }
}
