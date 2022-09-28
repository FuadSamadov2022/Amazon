@regression @api
Feature: Get All Entries

  @wip @api
  Scenario: Get All Entries
    Given User sends API request for the ENTRIES endpoint, expected status code: 200
    And User verifies count of total entries not null and more than 1425
    And User saves all Categories and Links
