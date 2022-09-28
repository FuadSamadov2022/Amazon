package com.amazon.pages;

import io.restassured.response.Response;

public class APIResponse {

    private Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
