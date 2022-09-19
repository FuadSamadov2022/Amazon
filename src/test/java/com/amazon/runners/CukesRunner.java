package com.amazon.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "junit:target/cucumber-results.xml",
                "html:target/cucumber-reports.html",
                "rerun:src/test/resources/rerun/rerun.txt"
        },
        features = {"src/test/resources/features"},
        glue = {"com/amazon/step_def"},
        monochrome = true,
//        dryRun = true,
        tags = "@wip"
)

public class CukesRunner {
}
