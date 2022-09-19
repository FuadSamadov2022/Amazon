package com.amazon.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Locale;

public class WebUtils {

    private static Logger logger = LogManager.getLogger(WebUtils.class.getSimpleName());

    public void waitFor(int timeout) {
        try {
            Thread.sleep(timeout * 1000L);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void waitFor(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public WebElement waitForClickability(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));

        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public WebElement waitForVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        WebElement webElement = null;
        try {
            webElement = wait.until(ExpectedConditions.visibilityOf(element));
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            logger.error(e.getMessage());
        }

        return webElement;
    }

    public void scrollToTheElementWithJS(WebElement element){
        logger.info("About to scroll into the view.");
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true)", element);
    }

    public boolean waitUntilTitleContains(String title, int timeout){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        try{
            return wait.until(ExpectedConditions.titleContains(title));
        } catch (TimeoutException e){
            logger.error(e.getMessage());
        }

        return false;
    }

    public String waitUntilTitleIs(String title){
        int attempt = 7;
        while (attempt > 0){
            waitFor(300L);
            String actualTitle = Driver.getDriver().getTitle();
            if(actualTitle.toLowerCase().contains(title.toLowerCase())){
                return actualTitle;
            }
            attempt--;
        }

        return "Actual Title is Different.\nTitle:" + Driver.getDriver().getTitle();
    }

    public void smartSendKeys(WebElement field, String message, int timeout){
       waitForClickability(field, timeout).clear();
       logger.info("About to send keys.");
       field.sendKeys(message);
    }

}
