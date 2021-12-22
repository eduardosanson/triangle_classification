package br.com.sanson.challenge;

import br.com.sanson.challenge.adapter.MapEventAdapter;
import br.com.sanson.challenge.adapter.assemble.MapToTriangleClassifyRequest;
import br.com.sanson.challenge.application.usecase.CreateTriangle;
import br.com.sanson.challenge.infrastructure.dao.RequestRepository;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
public class Handler implements RequestHandler<Map<String, Object>, Map<String, Object>> {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private MapEventAdapter adapter;
    private RequestRepository requestRepository;

    public Handler() {
        this.requestRepository = new RequestRepository(gson,
                                 new DynamoDB(Regions.fromName(System.getenv().get("AWS_DEFAULT_REGION"))));
        this.adapter = new MapEventAdapter(new CreateTriangle());
    }

    protected Handler(MapEventAdapter adapter,
                      RequestRepository requestRepository) {
        this.adapter = adapter;
        this.requestRepository = requestRepository;
    }

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> event, Context context)
    {
        LambdaLogger logger = context.getLogger();
        // log execution details
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("EVENT: " + gson.toJson(event));
        Map response = new HashMap();
        try {
            response.putAll(adapter.createTriangle(MapToTriangleClassifyRequest.assemble(event)));

        }catch (IllegalArgumentException ex){
            logger.log("WARNING: " + ex.getMessage() );
            response.put("message", ex.getMessage());
            response.put("statusCode", HttpStatus.SC_BAD_REQUEST);

        }catch (Exception ex){
            logger.log("ERROR: " + ex.getMessage() );
            response.put("statusCode", HttpStatus.SC_INTERNAL_SERVER_ERROR);
            response.put("message", ex.getMessage());
        } finally {
            logger.log("response: " + response);
            requestRepository.saveRequest(event, response, context);
        }


        return response;
    }

}
