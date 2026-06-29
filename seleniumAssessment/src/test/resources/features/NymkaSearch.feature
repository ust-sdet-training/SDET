Feature: Nykaa search
   As user i want to search shirt through the search bar

Background:
  Given I am browsing nykaa

@shah
Scenario: Successfully search with keyword
     When I look for "watch"
     And I choose the first product from the suggestion
     Then I should see the shirt pages collection


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