Feature: Catalog checkout
  As a shopper
  I want to find products, add them to my cart, and complete an order
  So that the retail purchase journey is covered in shared language

  Background:
    Given the login page is open

  @smoke
  Scenario: Sort products by low to high
    Given the catalog is opened
    When I sort products by "Price: Low to High"
    Then the products are sorted in ascending order

  @smoke @regression
  Scenario: Login to Checkout Flow
    Given the login page is open
    When I login with "customer@example.com" and "Password@123"
    Then checking the username
    Then the heading is displayed
    Then the page title contains "Welcome"
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
  Scenario Outline: Login to Checkout Flow
    Given the login page is open
    When I login with "customer@example.com" and "Password@123"
    Then checking the username
    Then the heading is displayed
    Then the page title contains "Welcome"
    When I navigate to the catalog page
    And I searched for the "<firstProduct>"
    And I added the first result to the cart
    Then the cart badge becomes 1
    When I opened the cart
    Then the cart has 1 line of item
    When I navigate to the catalog page
    And I searched for the "<secondProduct>"
    And I added the first result to the cart
    Then the cart badge becomes 2
    When I opened the cart
    Then the cart has 2 line of items
    When I placed the order
    Then the order is got confirmed

  Examples:
    | firstProduct | secondProduct |
    | lamp         | shoes         |
    | headphones   | lamp          |
    | shoes        | headphones    |


  @regression
  Scenario: Fresh cart is empty
    Given the login page is open
    When I login with "customer@example.com" and "Password@123"
    Then checking the username
    And the heading is displayed
    When I opened the cart
    Then the cart has 0 line of items

  @negative
  Scenario: Login with invalid credentials
    Given the login page is opened
    When I logged in with "wrong@example.com" and "WrongPassword"
    Then the login error message is displayed

  @negative
  Scenario: Demonstrate screenshot capture on failure
    Given the login page is opened
    When I logged in with "customer@example.com" and "Password@123"
    Then the cart badge becomes 99