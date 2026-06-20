Feature: Catalog cart checkout
  As a shopper
  I want to search the catalog, add a product to my cart, and complete checkout
  so that I can buy what I came for
  Verifying the above Feature and User Description for Gate 3 assignment

  Background:
    Given the catalog is open

    @smoke @first
    Scenario: Buy a single product end to end
      When I search for "mat"
      And I click on the first product
      And I update details to 6 mm, Teal, 1, Home delivery
      And I add the product to the cart
      Then the cart badge shows 1
      When I open the cart
      Then I see the cart has 1 line item
      And the product details are 6 mm, Teal, 1
      When I place the order
      Then the order is confirmed

    @smoke @second
    Scenario: Buy multiple products to cart and verifies cart count
      When I search for "mat"
      And I click on the first product
      And I add the product to the cart

      When I search for "shoes"
      And I click on the first product
      And I add the product to the cart

      When I search for "bottle"
      And I click on the first product
      And I add the product to the cart

      Then the cart badge shows 3
      When I open the cart
      Then I see the cart has 3 line item

    @regression @third
    Scenario Outline: Buying multiple products end to end
      When I search for "<product1>"
      And I click on the first product
      And I add the product to the cart

      When I search for "<product2>"
      And I click on the first product
      And I add the product to the cart

      When I search for "<product3>"
      And I click on the first product
      And I add the product to the cart

      Then the cart badge shows <count>

      When I open the cart
      Then I see the cart has <count> line item

    Examples:
      | product1   | product2 | product3  | count |
      | headphones | shoes    | backpack  | 3     |
      | mat        | bottle   | jacket    | 3     |
      | lamp       | kit      | tablet    | 3     |


    @regression @fourth
    Scenario: A fresh cart is empty
      When I open the cart
      Then I see the cart has 0 line items

    @negative @fifth
    Scenario Outline: Test fail due to invalid search of Non-existent product -> mouse
      When I search for "<product1>"
        And I click on the first product
        And I add the product to the cart

        When I search for "<product2>"
        And I click on the first product
        And I add the product to the cart

        When I search for "<product3>"
        And I click on the first product
        And I add the product to the cart

        Then the cart badge shows <count>

    Examples:
          | product1   | product2 | product3  | count |
          | headphones | shoes    | mouse     | 3     |
          | mat        | bottle   | jacket    | 3     |
          | lamp       | kit      | tablet    | 3     |