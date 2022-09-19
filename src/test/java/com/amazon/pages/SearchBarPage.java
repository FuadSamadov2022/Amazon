package com.amazon.pages;

import com.amazon.utils.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.amazon.step_def.Hooks.DEFAULT_TIMEOUT;

public class SearchBarPage extends BasePage {

    private static Logger logger = LogManager.getLogger(SearchBarPage.class.getSimpleName());

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBar;

    @FindBy(id = "nav-search-submit-button")
    private WebElement searchBtn;

    public void searchFor(String searchRequest) {
        logger.info("About to enter search request");

        waitForClickability(searchBar, DEFAULT_TIMEOUT).sendKeys(searchRequest);
        waitForClickability(searchBtn, DEFAULT_TIMEOUT).click();
    }

    public void selectAndClickOnItem(String itemToClick) {
        logger.info("About to scroll to the item: " + itemToClick);
        String xpath = "//span[contains(text(), '" + itemToClick + "')]";

        WebElement itemToScroll = Driver.getDriver().findElement(By.xpath(xpath));
        scrollToTheElementWithJS(itemToScroll);

        logger.info("About to click on the item: " + itemToClick);
        waitForClickability(itemToScroll, DEFAULT_TIMEOUT).click();
    }
}
