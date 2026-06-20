Feature: Catalog checkout
  As a shopper, I want to search the catalog add a product to my cart and complete checkout,
  So that I can buy what I came for and the team can prove that journey on every build

  Background:
    Given The catalog is open

  @smoke
  Scenario: Buy a single product end to end
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1
    When I open the cart
    Then The cart has 1 line item
    When I place the order
    Then The order is confirmed

  @smoke
  Scenario: Cart Badge reflects the number of items added
    When I search for "running"
    And I add the first result to the cart
    Then the cart badge shows 1


  @regression
  Scenario Outline: Buy "<product>" end to end
    When I search for "<product>"
    And I add the first result to the cart
    Then the cart badge shows <count>
    When I open the cart
    Then The cart has <count> line item
    When I place the order
    Then The order is confirmed

    Examples:
      | product    | count |
      | headphones | 1     |
      | shoes      | 1     |
      | laptop     | 1     |

  @regression
  Scenario: A fresh cart is empty
    When I open the cart
    Then The cart has 0 line item

  @negative
  Scenario: Buy a single product which is not in list
    When I search for "lamb"
    And I add the first result to the cart
