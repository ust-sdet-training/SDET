Feature: Catalog Checkout Test

As a shopper,I want to search the catalog,add a product to my cart,and complete checkout
so that I can buy what I came for -and so that the team can prove every journey works
on every build

Background:
  Given I go the Catalog page

@smoke
Scenario: Buy a product login to Checkout
  When I opened a Product "headphones"
  And I add a product in Cart
  Then The CartBadge shows 1
  When I open a Cart
  Then It shows cart lines 1
  When I placed the Order
  Then It shows Confirmed message

@smoke
Scenario: Cart Badge display
  When I opened a Product "shoes"
  And I add a product in Cart
  Then The CartBadge shows 1

@regression
Scenario Outline: Buy a product "<product>"
 When I opened a Product "<product>"
 And I add a product in Cart
 Then The CartBadge shows <count>
 When I open a Cart
 Then It shows cart lines <count>
 When I placed the Order
 Then It shows Confirmed message

Examples:
   |product| count|
   |water  | 1    |
   |skin   | 1    |
   |shoes  | 1    |

@regression
Scenario: A fresh cart to be Opened
 When I open a Cart
 Then It shows the cart line 0


@negative
Scenario: Cart count Error
    When I opened a Product "headphones"
    And I add a product in Cart
    Then The CartBadge shows 1
    When I open a Cart
    Then It shows cart lines 1
    When I placed the Order
    Then It shows Confirmed message
    When I open a Cart
    Then It shows the cart line 0