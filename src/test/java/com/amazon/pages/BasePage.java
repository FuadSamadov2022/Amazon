package com.amazon.pages;

import com.amazon.utils.ConfigurationReader;
import com.amazon.utils.Driver;
import com.amazon.utils.WebUtils;
import org.openqa.selenium.support.PageFactory;

public class BasePage extends WebUtils {
    public BasePage() {
        String testType = ConfigurationReader.get("testType");
        if (testType.equalsIgnoreCase("ui")) {
            PageFactory.initElements(Driver.getDriver(), this);
        }
    }
}
