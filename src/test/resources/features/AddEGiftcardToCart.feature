@regression @eGiftCard
Feature: Add EGift card to the Cart

  Background: Navigate to Amazon
    Given User Navigates to "Amazon" application
    And User verifies page title contains "Amazon.com"

  @wip
  Scenario Outline: Search and Add E-Gift Card with amount: <add_amount> to the cart
    When User enters search request "electronic gift card" in the Search Bar
    And User verifies page title contains "Electronic Gift Card"
    Then User select and click on "Starbucks Gift Cards - Email Delivery" item
    And User verifies page title contains "Starbucks"
    Then User selects "Fall Thank You" card design
    Then User select amount:"<add_amount>", enters To:"fuad@gmail.com", From:"Fuad", Message:"HB!", Quantity:"1" on the Gift Card Details
    And User select month:"October", date:"31", year:"2022" on the Gift Card Details
#    And User verifies entered date: "October 31 2022", actualFormat:"MM/dd/yyyy", expectedFormat:"MMMM dd yyyy"
    And User Validates Quantity:"1" and Cart Subtotal value:"$<add_amount>.00"
    And User click Add to card button

    Examples:
      | add_amount |
      | 100        |
      | 150        |
      | 50         |


