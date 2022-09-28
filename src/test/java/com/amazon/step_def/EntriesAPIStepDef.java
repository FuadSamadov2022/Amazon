package com.amazon.step_def;

import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.amazon.utils.Request.makeAPICall;
import static org.hamcrest.Matchers.*;

public class EntriesAPIStepDef extends BaseStep {

    private final static Logger testLogger = LogManager.getLogger(EntriesAPIStepDef.class.getSimpleName());

    @Given("User sends API request for the ENTRIES endpoint, expected status code: {int}")
    public void user_sends_api_request_for_the_entries_endpoint_expected_status_code(int expectedStatusCode) {
        Response response = makeAPICall("GET", "entries", new HashMap<>(), new HashMap<>(), new HashMap<>(), expectedStatusCode);
        pages.apiResponse().setResponse(response);
    }

    @Given("User verifies count of total entries not null and more than {int}")
    public void user_verifies_count_of_total_entries_not_null_and_more_than(int numberOfEntries) {
        testLogger.info("About to verify that number of the Entries is not NULL nad more than: " + numberOfEntries);
        Response response = pages.apiResponse().getResponse();
        response.then()
                .log().ifValidationFails()
                .body("$", hasKey("count"))
                .and()
                .body("count", not(hasValue(nullValue())))
                .and()
                .body("count", greaterThanOrEqualTo(numberOfEntries));
    }

    @Given("User saves all Categories and Links")
    public void user_saves_all_categories_and_links() {
        testLogger.info("About to save all Categories and Links");
        Response response = pages.apiResponse().getResponse();
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, ?>> allEntries = jsonPath.get("entries");

        int index = 1;
        for (Map<String, ?> entry : allEntries) {
            String link = entry.get("Link") + "";
            String category = entry.get("Category") + "";

            testLogger.info("Entry #" + index + "\n\tLink = " + link + "\n\tCategory = " + category);
            index++;
        }

    }

}
