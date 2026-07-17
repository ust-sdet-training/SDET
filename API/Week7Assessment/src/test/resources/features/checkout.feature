Feature: Catalog Page
  As a customer
  I want to find products, add items to my cart, and complete an order
  So that the retail purchase journey is covered in the test scenerios

  Background:
    Given the homepage is open


  @smoke @ui @api
  Scenario: Search Product Via UI and API
    When I search for "cap"
    Then "Everyday Cap" is displayed


  @e2e @smoke
  Scenario: Make an order
    Given "alice" is logged in
    When the homepage is open
    And I search for "desk lamp"
    Then "Focus Desk Lamp" is displayed
    When I move to "Focus Desk Lamp" product page
    Then "Focus Desk Lamp" product title is displayed
    When I change the quantity to 2
#    And I add product to cart
#    Then the cart line shows quantity 1 and the correct line total
#    When I go to the checkout page
#    And I place the order with a valid address
#    Then the order confirmation shows status "PLACED"




  @e2e @smoke
  Feature: Checkout places an order and the backend agrees

  Scenario: A logged-in customer checks out a two-item cart
    Given "alice" is logged in
    And she adds 2 x "SKU-BAG" (49900 paise each) to her cart
    When she checks out with a valid address
    Then the order confirmation shows status "PLACED"
    And GET /api/orders/{id} returns PLACED and totalPaise 99800
    And the orders table has exactly one PLACED row for alice

