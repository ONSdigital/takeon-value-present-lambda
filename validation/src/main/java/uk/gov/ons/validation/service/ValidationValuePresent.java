package uk.gov.ons.validation.service;

import uk.gov.ons.validation.entity.InputData;
import uk.gov.ons.validation.entity.OutputData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;


public class ValidationValuePresent {

    public String apply(String input) throws Exception {
        OutputData outputData = parseAndRunValidationRule(input);
        return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .writeValueAsString(outputData);
    }

    // Take any required source JSON, parse it into our data class, run the validation rule and then produce the output
    private OutputData parseAndRunValidationRule(String inputJson) {
        OutputData outputData;
        try {
            InputData inputData = new ObjectMapper().readValue(inputJson, InputData.class);
            outputData = runValidationRule(inputData);
        } catch (JsonProcessingException e) {
            outputData = new OutputData(null, null, null, "Error parsing source JSON: " + inputJson);
        } catch (Exception e) {
            outputData = new OutputData(null, null, null, "Miscellaneous error parsing JSON input parameters: " + inputJson);
        }
        return outputData;
    }

    // Take the given data and invoke the validation rule
    private OutputData runValidationRule(InputData inputData) {
        RuleValuePresent rule = new RuleValuePresent(inputData);
        return new OutputData(rule.getValueFormula(), rule.run(), inputData.getMetaData(), null);
    }

}
