Feature: Catalog checkout
  As a shopper
  I want to search the catalog, add a product to my cart, and complete checkout,
  So that  i can buy what i came for
  and the team can prove that journey works on every build.

  Background:
    Given the catalog is open

  @smoke
  Scenario: Buy a single product end to end
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1
    When I open the cart
    Then the cart has 1 line item
    When I place the order
    Then the order is confirmed

  @regression
  Scenario: Login to Checkout Flow
    Given the login page is open
    When I login with "customer@example.com" and "Password@123"
    Then the heading is displayed
    When I navigate to the catalog page
    And I searched for the "lamp"
    And I added the first result to the cart
    Then the cart badge becomes 1
    When I opened the cart
    Then the cart has 1 line of item
    When I navigate to the catalog page
    And I searched for the "shoes"
    And I added the first result to the cart
    Then the cart badge becomes 2
    When I opened the cart
    Then the cart has 2 line of items
    When I placed the order
    Then the order is got confirmed


  @smoke
  Scenario: Cart badge reflects the number of items added
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 1
    Given the catalog is open
    When I search for "shoes"
    And I add the first result to the cart
    Then the cart badge shows 2
    Given the catalog is open
    When I search for "lamp"
    And I add the first result to the cart
    Then the cart badge shows 3

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
      | product     | count |
      | headphones  | 1     |
      | shoes       | 1     |
      | lamp        | 1     |

  @regression
  Scenario: A fresh cart is empty
    When I open the cart
    Then the cart has 0 line items


  @negative
  Scenario: Deliberately failing scenario for screenshot evidence
    When I search for "headphones"
    And I add the first result to the cart
    Then the cart badge shows 5
