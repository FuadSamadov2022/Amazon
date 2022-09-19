package com.amazon.step_def;


import com.amazon.utils.ConfigurationReader;
import com.amazon.utils.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.Duration;

public class Hooks extends BaseStep{

    private final static Logger testLogger = LogManager.getLogger(ConfigurationReader.class.getSimpleName());
    public final static int DEFAULT_TIMEOUT = 10;

    @Before(order = 1)
    public void setUpScenario(Scenario scenario) {
        testLogger.info("Started execution test Scenario - " + scenario.getName());
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : ConfigurationReader.get("browser");

        if (!browser.contains("remote")) {
            Driver.getDriver().manage().window().maximize();
        }
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    @After(order = 1)
    public void tearDownScenario(Scenario scenario) {
        testLogger.info("Finished execution test Scenario - " + scenario.getName());
        testLogger.info("Scenario status - " + scenario.getStatus().toString());
        testLogger.info("------------------------- After hook starts -------------------------");

        if (scenario.isFailed()) {
            testLogger.error("** Error test is fail **");
            testLogger.error("** Taking Screenshot **");
            byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        pages.webUtils().waitFor(200L);
        Driver.closeDriver();
        testLogger.info("Driver closed");
        testLogger.info("------------------------- After hook ends -------------------------");
        testLogger.info("-------------------------------------------------------------------");
    }

}
