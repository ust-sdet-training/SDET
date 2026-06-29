Feature: Catalog checkout
    As as shopper
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

    @regressed
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
        Then the cart has 0 line item

    @regression
    Scenario: Add multiple quantities of a product
        When I search for "shoes"
        And I add the first result to the cart
        And the catalog is open
        When I search for "bags"
        And I add the first result to the cart
        Then the cart badge shows 2
        When I open the cart
        Then the cart has 2 line items


    @negative
    Scenario: Search for a product that does not exist
        When I search for "abcdef"
        Then the cart badge shows 0

    @regression
    Scenario: Remove a product from the cart
        When I search for "shoes"
        And I add the first result to the cart
        Then the cart badge shows 1
        When I open the cart
        And I remove the item from the cart
        Then the cart has 0 line items
        And the cart badge shows 0


    @regression
    Scenario: Sort the products and add to cart
        When I sort products by "Price: Low to High"
        And I add the first result to the cart
        Then the cart badge shows 1

