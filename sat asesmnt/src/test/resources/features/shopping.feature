@smoke @e2e
Feature: Shopping flow

  Background:
    Given the catalog is open

  @smoke
  Scenario: Buy a single product end-to-end
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1
    When I open the cart
    Then the cart has 1 line item
    When I place the order
    Then the order is confirmed

  @smoke
  Scenario: Cart badge reflects number of items added
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1

  @regression
  Scenario Outline: Buy across several products
    When I search for "<product>"
    And I add the first result to the cart
    When I open the cart
    Then the cart has 1 line item
    When I place the order
    Then the order is confirmed

    Examples:
      | product  |
      | headphones    |
      | shoes     |

      

  @regression
  Scenario: Fresh cart is empty and shows valid empty-state
    When I open the cart
    Then the cart has 0 line items

  @negative
  Scenario: Deliberately failing scenario to capture screenshot
    When I search for "headphones"
    And I add the first result to the cart
    When I open the cart
    Then the cart has 99 line items
