package com.amazon.step_def;

import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchStepDef extends BaseStep{

    private static Logger logger = LogManager.getLogger(SearchStepDef.class.getSimpleName());


    @Then("User enters search request {string} in the Search Bar")
    public void user_enters_search_request_in_the_search_bar(String searchRequest) {
        logger.info("About to enter search request: " + searchRequest + " in the search bar.");
        pages.searchBarPage().searchFor(searchRequest);
    }

    @Then("User select and click on {string} item")
    public void user_select_and_click_on_item(String itemToClick) {
        logger.info("About to select and click on the: " + itemToClick);
        pages.searchBarPage().selectAndClickOnItem(itemToClick);
    }
}
