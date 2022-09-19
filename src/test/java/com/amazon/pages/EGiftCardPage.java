package com.amazon.pages;


import com.amazon.utils.DateTimeUtils;
import com.amazon.utils.Driver;
import com.amazon.utils.WebUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.amazon.step_def.Hooks.DEFAULT_TIMEOUT;

public class EGiftCardPage extends BasePage {

    private static Logger logger = LogManager.getLogger(EGiftCardPage.class.getSimpleName());

    @FindBy(xpath = "//a[text()='See more']")
    private WebElement seeMoreBtn;

    @FindBy(xpath = "//span[contains(text(), 'Use this design')]//../input")
    private WebElement useDesignBtn;

    @FindBy(id = "gc-order-form-recipients")
    private WebElement emailToField;

    @FindBy(id = "gc-order-form-senderName")
    private WebElement fromField;

    @FindBy(id = "gc-order-form-message")
    private WebElement messageField;

    @FindBy(id = "gc-order-form-quantity")
    private WebElement quantityField;

    @FindBy(xpath = "//i[contains(@class, 'a-icon a-icon-calendar')]")
    private WebElement calendarIcon;

    @FindBy(xpath = "//a[@data-action='a-cal-next-month']")
    private WebElement nextMonthBtn;

    @FindBy(xpath = "//h4[@class='a-cal-month-header']")
    private WebElement monthHeaderValue;

    @FindBy(xpath = "//input[contains(@aria-label, 'Date')]")
    private WebElement dateInputField;

    @FindBy(id = "gc-buy-box-text")
    private WebElement quantityAddToCart;

    @FindBy(xpath = "//p[@id='gc-buy-box-text']/span")
    private WebElement subTotalAmount;

    @FindBy(id = "gc-buy-box-atc-button")
    private WebElement addToCartBtn;

    public void selectGiftCardStyle(String styleToSelect) {
        String itemXpath = "//ul[@id='gc-design-mini-picker']/li//img[@alt='" + styleToSelect + "']";
        WebElement item = Driver.getDriver().findElement(By.xpath(itemXpath));
        boolean isStyleVisible = item.isDisplayed();

        if (!isStyleVisible) {
            waitForClickability(seeMoreBtn, DEFAULT_TIMEOUT).click();
            selectItemDesignFromSeeMore(styleToSelect);
        }

        waitForClickability(item, DEFAULT_TIMEOUT).click();
    }

    public void enterGiftCardDetails(String amount, String emailToStr, String fromStr, String messageStr, String quantityStr) {
        selectAmount(amount);
        smartSendKeys(emailToField, emailToStr, DEFAULT_TIMEOUT);
        smartSendKeys(fromField, fromStr, DEFAULT_TIMEOUT);
        smartSendKeys(messageField, messageStr, DEFAULT_TIMEOUT);
        smartSendKeys(quantityField, quantityStr, DEFAULT_TIMEOUT);
    }

    private void selectAmount(String amount) {
        logger.info("About to select amount: " + amount);
//        String xpath = "//button[contains(@id , 'gc-mini-picker-amount') and @value='" + amount + "']";
        String xpath = "//button[contains(@id , 'gc-mini-picker-amount') and contains(text(), '$" + amount + "')]";
        waitForClickability(Driver.getDriver().findElement(By.xpath(xpath)), DEFAULT_TIMEOUT).click();
    }

    private void selectItemDesignFromSeeMore(String styleToSelect) {
        logger.info("About to select Design item from See More Designs.");
        String xpath = "//span[contains(text(), '" + styleToSelect + "')]";
        WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
        waitForClickability(element, DEFAULT_TIMEOUT).click();
        waitForClickability(useDesignBtn, DEFAULT_TIMEOUT).click();
    }

    public void selectMothAndYear(String monthAndYear) {
        logger.info("About to select month and year: " + monthAndYear);
        waitForClickability(calendarIcon, DEFAULT_TIMEOUT).click();

        while (true) {
            String currentMonthAndYear = waitForVisibility(monthHeaderValue, DEFAULT_TIMEOUT).getText();
            if (currentMonthAndYear.equalsIgnoreCase(monthAndYear)) {
                break;
            }

            waitForClickability(nextMonthBtn, DEFAULT_TIMEOUT).click();
        }
    }

    public void selectDate(String date) {
        logger.info("About to select date: " + date);
        String xpath = "//tbody[@class='a-cal-days']//td/a[contains(text(), '" + date + "')]";
        waitForClickability(Driver.getDriver().findElement(By.xpath(xpath)), DEFAULT_TIMEOUT).click();
    }

    public String verifyEnteredDate(String originalFormat, String targetedFormat) {
        logger.info("About to get actual date");
        waitFor(1);
        String notFormattedDate = waitForVisibility(dateInputField, DEFAULT_TIMEOUT).getAttribute("textContent");
        logger.info("notFormattedDate: " + notFormattedDate);
        return new DateTimeUtils().convertDate(originalFormat, targetedFormat, notFormattedDate);
    }

    public String getQuantityAddToCart() {
        logger.info("About to get quantity");
        return waitForVisibility(quantityAddToCart, DEFAULT_TIMEOUT).getText();
    }

    public String getSubtotalAddToCart() {
        logger.info("About to get quantity");
        return waitForVisibility(subTotalAmount, DEFAULT_TIMEOUT).getText();
    }

    public void clickAddToCartButton() {
        waitForClickability(addToCartBtn, DEFAULT_TIMEOUT).click();
    }
}
