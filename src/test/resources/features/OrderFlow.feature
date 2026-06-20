Feature: Product Order

  Background:
    Given the sdet retail app is open

  @smoke
  Scenario Outline: Search "<product>" and Place a order
    When I click on Products
    Then the Product Catalog page is open
    When I search for "<product>"
    Then I view the product
    When I add the product to cart
    Then the cart page is open and cart badge updated to 1
    When I click on proceed to checkout
    When I click place order
    Then verify the order is created
    Examples:
      | product       |
      | product1.name |
      | product2.name |
      | product3.name |
      | product4.name |
