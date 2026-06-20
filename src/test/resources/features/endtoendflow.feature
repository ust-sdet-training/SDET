Feature:checkout
  As a shopper
  I want to find products,add them to my cart,and complete an order
  So that the retail purchase journey is covered in shared language

Background:
      Given the catalog is open

@smoke
Scenario: Buy a single product end to end
    When I opened a product "headphones"
    And I add a first result in cart badge
    Then the cart badge shows 1
    When I open a cart
    Then the cart has 1 line item
    When I place the order
    Then the order is confirmed

@smoke
Scenario: Cart badge reflects the number of items added
    When I opened a product "headphones"
    And I add a first result in cart badge
    Then the cart badge shows 1

@regression
Scenario Outline: Buy several products

    When I opened a product "<product>"
    And I add a first result in cart badge
    When I open a cart
    Then the cart has 1 line item
    When I place the order
    Then the order is confirmed

Examples:
    | product    |
    | headphones |
    | shoes   |
    | laptop     |

@regression
Scenario: Fresh cart is empty
    When I open a cart
    Then the cart has 0 line items

@negative
Scenario: Demonstrate failure screenshot
    When I opened a product "headphones"
    And I add a first result in cart badge
    Then the cart badge shows 2