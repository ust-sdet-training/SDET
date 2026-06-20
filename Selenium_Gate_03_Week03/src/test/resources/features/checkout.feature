Feature: Catalog checkout
  As a shopper,
  I want to search the catalog,add a product to my cart,
  add complete checkout, so that I can buy what I came for and the team
  can prove that journey works on every build

  Background:
    Given I am browsing the product catalog

  @smoke
  Scenario: Shopper successfully buys a single item
    When I look for "Ceramic Dinner Set"
    And I choose the first product from the results and add it to my cart
    Then I should see the cart badge updated to 1

    When I navigate to my cart
    Then I should see 1 item listed in the cart

    When I proceed to checkout
    Then my order should be placed successfully

  @smoke
  Scenario: Shopper sees cart count increase when adding multiple items
    When I look for "Ceramic Dinner Set"
    And I add the first available product to the cart
    And I look for "bottle"
    And I add the first available product to the cart
    Then I should see the cart badge updated to 2

  @regression
  Scenario Outline: Shopper completes purchase for various products
    When I look for "<product>"
    And I add the first available result to the cart
    Then I should see the cart badge updated to <count>

    When I navigate to my cart
    Then I should see <count> item in the cart

    When I proceed to checkout
    Then my order should be placed successfully

    Examples:
      | product   | count |
      | Skin      | 1     |
      | Snack     | 1     |
      | bottle    | 1     |
      | backpack  | 1     |

  @regression
  Scenario: Shopper opens cart and finds it empty initially
    When I navigate to my cart
    Then I should see no items in the cart

  @negative
  Scenario: Shopper sees incorrect cart badge count (intentional failure)
    When I look for "headphones"
    And I add the first available product to the cart
    Then I should see the cart badge updated to 1