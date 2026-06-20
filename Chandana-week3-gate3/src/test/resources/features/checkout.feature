Feature: Catalog checkout

    As a shopper
    I want to find products, add them to my cart, and complete an order
    So that the retail purchase journey is covered in shared language

Background:
    Given the catalog is open

@smoke
Scenario: Buy a single product end to end
    When I search for "travel backpack"
    And I open the first matching product
    And I add the product to the cart
    Then the cart badge shows 1
    When I open the cart
    Then the cart has 1 line items
    When I proceed to checkout
    And I place the order
    Then the order is confirmed


@smoke
Scenario: Cart badge reflects the number of items added
    When I search for "travel backpack"
    And I open the first matching product
    And I add the product to the cart
    Then the cart badge shows 1

@regression
Scenario Outline: Buy a "<product>" end to end
    When I search for "<product>"
    And I open the first matching product
    And I add the product to the cart
    Then the cart badge shows <count>
    When I open the cart
    Then the cart has <count> line items
    When I proceed to checkout
    And I place the order
    Then the order is confirmed

Examples:
  | product         | count |
  | running shoes   | 1     |
  | travel backpack | 1     |
  | headphones      | 1     |


@regression
Scenario: A fresh cart is empty
    When I open the cart
    Then the cart has 0 line items

@negative
Scenario: Deliberate failure to demonstrate screenshot capture
    When I search for "travel backpack"
    And I open the first matching product
    And I add the product to the cart
    Then the cart badge shows 999
