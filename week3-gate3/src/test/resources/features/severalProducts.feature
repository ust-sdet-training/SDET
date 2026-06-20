Feature: Buying accross Different products same way

  Background:
    Given the catalog is open




  @regression
  Scenario Outline: Buy across several products

    When I search for "<product>"
    And I add the first result to the cart
    Then the cart badge shows 1
    When I open the cart
    Then the cart has 1 line item
    When I place the order
    Then the order is confirmed

    Examples:
      | product         |
      | headphones      |
      | shoes           |
      | lamp            |
      | Running Shoes   |
      | Travel Backpack |
      | Yoga Mat        |
      | Rain Jacket     |
