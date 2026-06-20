Feature: Validate Cart Functionalities

  Background:
    Given the sdet retail app is open

  @regression
  Scenario: A fresh cart is empty
    When I click the cart
    Then the cart has 0 products

  @smoke
  Scenario: Cart Badge reflects count accordingly as products added
    When I click on Products
    Then the Product Catalog page is open
    When I search for "product1.name"
    Then I view the product
    When I add the product to cart
    Then the cart page is open and cart badge updated to 1
    And I click on Products
    When I search for "product2.name"
    Then I view the product
    When I add the product to cart
    Then the cart page is open and cart badge updated to 2
    And I click on Products
    When I search for "product3.name"
    Then I view the product
    When I add the product to cart
    Then the cart page is open and cart badge updated to 3
