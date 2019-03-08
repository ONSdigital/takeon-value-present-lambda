package uk.gov.ons.validation.serverless;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import uk.gov.ons.validation.service.ValidationValuePresent;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOG = LogManager.getLogger(Handler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        LOG.info("received: {}", input);

        String outputData = getJsonBody(input);

        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(outputData)
                .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                .build();
    }


    private String getJsonBody(Map<String, Object> input) {
        String outputData = "empty";

        try {  // try getting JSON from API Gateway input
            outputData = new ValidationValuePresent().apply(input.get("body").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (outputData.equals("empty")) {
            try { // try getting JSON from AWS console event
                outputData = new ValidationValuePresent().apply(input.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return outputData;
    }

}
