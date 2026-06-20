Feature: Cart Badge

  Background:
    Given the catalog is open



  @smoke
  Scenario: Cart badge reflects the number of items added for multiple items

    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1
    Given the catalog is open
    When I search for "shoes"
    And I add the first result to the cart
    Then the cart badge shows 2

  @smoke
  Scenario: Cart badge reflects the number of items added for Single item

    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1
