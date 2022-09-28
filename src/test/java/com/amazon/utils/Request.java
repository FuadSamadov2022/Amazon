package com.amazon.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Request extends APIUtils {

    private final static Logger testLogger = LogManager.getLogger(Request.class.getSimpleName());

    public static Response makeAPICall(String method, String endpoint, Map<String, ?> bodyMap, Map<String, ?> queryParams, Map<String, ?> pathParams, int statusCode) {
        testLogger.info("About to perform API request");
        testLogger.info(String.format("METHOD: %s, ENDPOINT: %s, STATUS CODE: %s", method, endpoint, statusCode));
        testLogger.info(String.format("BODY: %s \nQUERY PARAMS: %s \nPATH PARAMS: %s", bodyMap.toString(), queryParams.toString(), pathParams.toString()));

        RequestSpecification requestSpecification = getRequestSpecifications(endpoint);

        Response response = given()
                .log().all()
                .spec(requestSpecification)
                .body(bodyMap.toString())
                .pathParams(pathParams)
                .queryParams(queryParams)
                .when()
                .request(method);

        new WebUtils().waitFor(1);

        try{
           response.then().spec(basicValidation(statusCode));
        }catch (Exception | AssertionError e){
            testLogger.error("Failed basic Validation");
            Assert.fail(response.body().asPrettyString());
        }

        return response;
    }

}
