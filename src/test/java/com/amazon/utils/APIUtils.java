package com.amazon.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.Matchers.lessThan;

public class APIUtils {

    private final static Logger testLogger = LogManager.getLogger(APIUtils.class.getSimpleName());

    protected static ResponseSpecification basicValidation(int statusCode){
        testLogger.info("Inside basicValidation");
        testLogger.info("About to perform basic validation of the status code and response timeout.");
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(10000L))
                .build();
    }

    protected static RequestSpecification getRequestSpecifications(String endpoint){
        testLogger.info("Inside getRequestSpecifications");
        testLogger.info("Endpoint = " + endpoint);
        String baseURL = ConfigurationReader.get("apiBaseURL");
        testLogger.info("Base URL: " + baseURL);
        return new RequestSpecBuilder()
                .setBaseUri(baseURL)
                .setContentType(ContentType.JSON)
                .setBasePath(endpoint)
                .build();
    }

}
