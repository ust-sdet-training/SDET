Feature: Catalog checkout

As a shopper
I want to find products, add them to my cart, and complete an order
So that the retail purchase journey is covered in shared language

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


@smoke
Scenario Outline: Login using valid credentials
    When the user enters "<username>" and "<password>"
    And clicks sing in button
    Then the user should be redirected to home page

    Examples:
    | username            | password     |
    |customer@example.com | Password@123 |


@smoke
Scenario: Sort products by low to high
    Given the catalog is opened
    When I sort products by "Price: Low to High"
    Then the products are sorted in ascending order

@regression
Scenario Outline: Cart badge updates after adding products
    Given the catalog is opened
    When I search for "<product>"
    And I add the first result to the cart
    Then the cart badge shows <count>


Examples:
| product    | count |
| lamp       | 1     |
| shoes      | 1     |
| headphones | 1     |



@smoke @regression
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
    | product    | count |
    | headphones | 1     |
    | shoes      | 1     |
    | lamp       | 1     |



@exercise
Scenario: A fresh cart is empty
    When I open the cart
    Then the cart has 0 line items

@negative
Scenario: Login using invalid credentials
    Given the login page is open
    When the user enters "invalid@example.com" and "password"
    And clicks sign in button
    Then the login should fail
    And an error message "<message>" should be displayed