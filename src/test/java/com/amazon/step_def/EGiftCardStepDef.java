package com.amazon.step_def;

import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class EGiftCardStepDef extends BaseStep {

    private static Logger logger = LogManager.getLogger(EGiftCardStepDef.class.getSimpleName());

    @Then("User selects {string} card design")
    public void user_selects_card_design(String designName) {
        logger.info("About to select Design: " + designName);
        pages.eGiftCardPage().selectGiftCardStyle(designName);
    }

    @Then("User select amount:{string}, enters To:{string}, From:{string}, Message:{string}, Quantity:{string} on the Gift Card Details")
    public void user_select_amount_enters_to_from_message_quantity_on_the_gift_card_details(String amount, String emailTo, String from, String message, String quantity) {
        logger.info(String.format("About to enter amount:%s, email to:%s, from:%s, message:%s, quantity:%s", amount, emailTo, from, message, quantity));
        pages.eGiftCardPage().enterGiftCardDetails(amount, emailTo, from, message, quantity);
    }

    @Then("User select month:{string}, date:{string}, year:{string} on the Gift Card Details")
    public void user_select_month_date_year_on_the_gift_card_details(String month, String date, String year) {
        pages.eGiftCardPage().selectMothAndYear(month + " " + year);
        pages.eGiftCardPage().selectDate(date);
    }

    @Then("User verifies entered date: {string}, actualFormat:{string}, expectedFormat:{string}")
    public void user_verifies_entered_date_actual_format_expected_format(String expectedDate, String actualFormat, String expectedFormat) {
        String actualDate = pages.eGiftCardPage().verifyEnteredDate(actualFormat, expectedFormat);
        Assert.assertEquals("Expected Date is not equals actual Date", expectedDate, actualDate);
    }

    @Then("User Validates Quantity:{string} and Cart Subtotal value:{string}")
    public void user_validates_quantity_and_cart_subtotal_value(String quantity, String cartSubTotal) {
        String actualQuantity = pages.eGiftCardPage().getQuantityAddToCart();
        Assert.assertTrue(actualQuantity.contains(quantity));

        String actualSubTotal = pages.eGiftCardPage().getSubtotalAddToCart();
        Assert.assertEquals(cartSubTotal, actualSubTotal);
    }

    @Then("User click Add to card button")
    public void user_click_add_to_card_button() {
        logger.info("About to click Add TO Cart button");
        pages.eGiftCardPage().clickAddToCartButton();
    }
}
