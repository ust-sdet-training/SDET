Feature: Amazon product search
  As a customer
  I want to search for a product and validate its details before viewing the product page

  Scenario: Search for a wireless mouse and validate the product details
    Given I open Amazon homepage
    When I search for "wireless mouse"
    Then the search results page should load
    And I capture all displayed product names
    And I open the first displayed product
    Then the product title should be displayed
    And the product price should be available
    And the product rating should be visible when available
    Then the product page should show the selected product
