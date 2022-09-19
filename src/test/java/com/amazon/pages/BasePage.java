package com.amazon.pages;

import com.amazon.utils.Driver;
import com.amazon.utils.WebUtils;
import org.openqa.selenium.support.PageFactory;

public class BasePage extends WebUtils {
    public BasePage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }
}
