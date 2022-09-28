package com.amazon.step_def;

import com.amazon.utils.ConfigurationReader;
import com.amazon.utils.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;


public class CommonSteps extends BaseStep {

    private static Logger logger = LogManager.getLogger(CommonSteps.class.getSimpleName());

    @Given("User Navigates to {string} application")
    public void user_navigates_to_application(String applicationName) {
        logger.info("About navigate to: " + applicationName);
        applicationName = applicationName.toLowerCase();
        String applicationURL = ConfigurationReader.get(applicationName);
        Driver.getDriver().navigate().to(applicationURL);
        logger.info("Successfully Navigated to the application: " + applicationName);

        String actualURL = Driver.getDriver().getCurrentUrl();
        logger.info("About to verify actual URL contains expected.");
        Assert.assertTrue("Actual URL doesn't contains expected.", actualURL.contains(applicationURL));
    }

    @When("User verifies page title contains {string}")
    public void user_verifies_page_title_contains(String expectedTitle) {
        logger.info("About to verify Actual Title contains expected.");
        String actualTitle = pages.webUtils().waitUntilTitleIs(expectedTitle).toLowerCase();
        expectedTitle = expectedTitle.toLowerCase();
        Assert.assertTrue("Actual Title doesn't contains expected. Actual Title: " + actualTitle, actualTitle.contains(expectedTitle));
    }

}
