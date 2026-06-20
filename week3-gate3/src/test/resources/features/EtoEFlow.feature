Feature: End to End Flow

  Background:
    Given the catalog is open

  @smoke
  Scenario: Buy a single product end to end
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1
    When I open the cart
    Then the cart has 1 line item
    When I place the order
    Then the order is confirmed

  @smoke @login
  Scenario: Buy a single product end to end with login
    Given the login page is open
    When I login with "customer@example.com" and "Password@123"
    Then the heading is displayed
    When I navigate to the catalog page
    Then the catalog page is displayed
    When I search for "Running Shoes"
    And I add the first result to the cart
    Then the cart badge shows 1
    When I open the cart
    Then the cart has 1 line item
    When I place the order
    Then the order is confirmed