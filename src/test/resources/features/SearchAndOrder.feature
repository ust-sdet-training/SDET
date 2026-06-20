Feature: Product Search and Order
  As a Shopper,
  I want to search the catalog, add a product to my cart, and complete checkout, so that I can buy what I came for
  and the user journey is covered in shared language

  Background:
    Given the sdet retail app is open

  @smoke
  Scenario: Search and Place a order
    When I click on Products
    Then the Product Catalog page is open
    When I search for "product1.name"
    Then I view the product
    When I add the product to cart
    Then the cart page is open and cart badge updated to 1
    When I click on proceed to checkout
    When I click place order
    Then verify the order is created


