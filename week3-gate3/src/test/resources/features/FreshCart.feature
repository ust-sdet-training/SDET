Feature: Fresh Cart

  Background:
    Given the catalog is open

  @regression
  Scenario: Fresh cart is empty
    When I open the cart
    Then the cart has 0 line items
