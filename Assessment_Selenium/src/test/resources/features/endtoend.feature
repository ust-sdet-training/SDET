Feature: Login to Checkout

As a User,I need to login to the Retail Website ,find a product
,add to the cart and place the order and also i need to see the maximum products can be buy
on a same product

Background:
  Given the User is Logged in with Valid Credentials

@smoke
Scenario Outline:Place the order as a Logged User
  When I go to Catalog page
  And I search for "headphones" and select it
  And I select the quantity as 2
  And I added it to the Cart
  Then the cart shows count as 2
  When I Place the Order
  Then It Shows order Confirmed

@regression
Scenario Outline:Place the order as a Logged User
  When I go to Catalog page
  And I search for <product> and select it
  And I select the quantity as <quantity>
  And I added it to the Cart
  Then the cart shows count as <count>
  When I Place the Order
  Then It Shows order Confirmed

  Examples:
    |product  |quantity|count|
    |"water"    |5       |5    |
    |"skin"     |2       |2    |
    |"lamp"     |1       |1    |

