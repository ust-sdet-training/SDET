Feature: Retail catalog to checkout journey

  @smoke
  Scenario: Buy a single product end to end
    Given the shopper opens the catalog
    When the shopper searches for "Running Shoes"
    And adds the product to the cart
    And the shopper opens the cart
    Then the cart contains 1 line item
    When the shopper captures the cart total and completes checkout
    Then the order is confirmed

  @smoke
  Scenario: Cart badge reflects the number of items added
    Given the shopper opens the catalog
    When adds "Travel Backpack" to the cart
    Then the cart badge shows 1

  @regression
  Scenario Outline: Buy several products through the reusable journey
    Given the shopper opens the catalog
    When adds "<product>" to the cart
    And the shopper opens the cart
    Then the cart contains 1 line item
    When the shopper captures the cart total and completes checkout
    Then the order is confirmed

    Examples:
      | product                    |
      | Running Shoes              |
      | Travel Backpack            |
      | Noise Canceling Headphones |

  @regression
  Scenario: A fresh cart has no line items
    Given the shopper opens the catalog
    When the shopper opens the cart
    Then the fresh cart is empty

  @negative
  Scenario: Deliberate failure demonstrates screenshot capture
    Given the shopper opens the catalog
    When adds "Yoga Mat" to the cart
    Then the cart badge shows 2
