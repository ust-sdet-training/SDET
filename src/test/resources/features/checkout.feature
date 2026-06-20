Feature: Catalog checkout
  As a shopper, I want to search the catalog,
  add a product to my cart, and complete checkout,
  so that I can buy what I came for -
  and the team can prove that journey works on every build

Background:
  Given The catalog page is open

  @smoke
  Scenario: The full cart flow for product
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1
    When I open the cart
    Then the cart has 1 line item
    When I place the order
    Then The order is confirmed


  @smoke
    Scenario: Check cart badge is correct
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1
    When The catalog page is open
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 2


  @regression
  Scenario Outline: Buy "<product>" end to end
    When I search for "<product>"
    And I add the first result to the cart
    Then the cart badge shows <count>
    When I open the cart
    Then the cart has 1 line item
    And I place the order
    Then The order is confirmed

    Examples:
      | product    | count |
      | headphones | 1     |
      | shoes      | 1     |
      | swift      | 1     |


  @regression
  Scenario: A fresh cart is empty
    When I open the cart
    Then the cart has 0 line items


  @negative
  Scenario: Adding items more than available
    When I search for "kids"
    And I add the first result to the cart
    Then the cart badge shows 1
    When The catalog page is open
    When I search for "kids"
    And I add the first result to the cart
    Then the cart badge shows 2
    When The catalog page is open
    When I search for "kids"
    And I add the first result to the cart
    Then the cart badge shows 3
    When The catalog page is open
    When I search for "kids"
    And I add the first result to the cart
    Then the cart badge shows 4
    When The catalog page is open
    When I search for "kids"
    And I add the first result to the cart
    Then the cart badge shows 5
    When The catalog page is open
    When I search for "kids"
    And I add the first result to the cart
    Then  the cart badge shows 6
