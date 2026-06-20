Feature: Faling Scenario

  Background:
    Given the catalog is open

  @negative
  Scenario: After adding one item to cart the cart count should be 1 we checked for 2
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 2

  @negative
  Scenario: Checking Login with Invalid Credientials
    Given the login page is open
    When I login with "wrong@example.com" and "Password@1143"
    Then the heading is displayed
