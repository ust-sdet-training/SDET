Feature: Catalog checkout
    As as shopper
    I want to find products, add them to my cart, and complete an order
    So that the retail purchase journey is covered in shared language

    Background:
        Given the catalog is open

    @smoke
    Scenario: Buy a single product from catalog
        When I search for "headphones"
        And the first product title is "Noise Canceling Headphones"
        And I add the first item to the cart
        Then the cart badge shows 1
        When I open the cart
        Then the cart has 1 line item
        When I place the order
        Then the order is confirmed

    @smoke
    Scenario: Cart badge reflects number of items added
        When I search for "headphones"
        And the first product title is "Noise Canceling Headphones"
        And I add the first item to the cart
        And the catalog is open
        And I search for "shoes"
        And the first product title is "Running Shoes"
        And I add the first item to the cart
        Then the cart badge shows 2

    @regression
    Scenario Outline: Buy across several products using examples
        When I search for "<product>"
        And I add the first item to the cart
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

    @regression
    Scenario: A fresh cart is empty
        When I open the cart
        Then the cart has 0 line item


    @negative
    Scenario: Deliberately checking incorrect cart count
        When I search for "headphones"
        And the first product title is "Noise Canceling Headphones"
        And I add the first item to the cart
        And the catalog is open
        And I search for "shoes"
        And I add the first item to the cart
        Then the cart badge shows 1