Feature: catalog product checkout
  User story,
  As a shopper,I want to search the catalog, add a product to my cart, and complete checkout, so that i can
  buy what i came for, and the team can prove that journey works on every build.
  the flow under test:
  Open catalog-> search product -> Add to cart-> Open cart-> Checkout -> order confirm
  Background:
    Given the catalog is open

    @smoke
    Scenario: Complete purchase of a single product
      When I search for "headphones"
      And I add the first product from the search results to the cart
      Then the cart badge should display 1
      When I navigate to the cart
      Then the cart should contain 1 item
      When I proceed to checkout and place the order
      Then the order should be successfully confirmed

  @regression
  Scenario Outline: Purchase different products successfully
    When I search for "<product>"
    And I add the first product from the results to the cart
    Then the cart badge reflects <count>
    When I open the cart
    Then the cart contains <count>
    When I complete the checkout process
    Then the order is successfully confirmed

    Examples:
      | product    | count |
      | headphones | 1     |
      | shoes      | 1     |
      | lamp       | 1     |


  @regression
  Scenario: Verify a newly opened cart is empty
    When I open the cart
    Then the cart contains <count> line items
    Examples:
      | count |
      | 0     |
      | 0     |
      | 0     |


  @negative
  Scenario: Demonstrate failure screenshot capture
    When I search for "headphones"
    And I add the first product from the search results to the cart
    Then the cart badge reflects 2 items