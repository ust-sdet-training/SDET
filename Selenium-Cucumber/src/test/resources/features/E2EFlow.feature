Feature: E2E flow from Catalog to Checkout
  As a shopper, I want to find products, add them to my cart, and complete an order.
  So that the retail purchase journey is covered in shared language

  Background:
    Given the catalog is open

    @smoke
    Scenario: Buy a single product end to end
      When I search for "headphones"
      And I add the first result to the cart
      Then the cart badge shows 1
      When I open the cart
      Then the cart has 1 line item
      Then I verified the price
      When I place the order
      Then the order is confirmed

    @smoke
    Scenario: Cart Badge reflects the number of items added
      Then the cart badge shows 0
      When I search for "shoes"
      And I add the first result to the cart
      Then the cart badge shows 1

    @regression
    Scenario Outline: Buy "<product>" end to end
      When I search for "<product>"
      And I add the first result to the cart
      Then the cart badge shows <count>
      When I open the cart
      Then the cart has <count> line item
      When I place the order
      Then the order is confirmed

      Examples:
        | product    | count |
        | headphones | 1     |
        | shoes      | 1     |
        | lamp       | 1     |

    @regression
    Scenario: A fresh cart is empty
      When I open the cart
      Then the cart has 0 line items

    @negative
    Scenario: A fresh cart is not empty
      When I open the cart
      Then the cart has items