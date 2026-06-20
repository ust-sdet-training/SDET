Feature: Catalog checkout
    As a shopper
    I want to search the catalog, add a product to my cart, and complete checkout,
    so that i can buy what i came for and the team can prove that the journey on every build

    Background:
        Given the catalog is open

    @smoke
    Scenario: Buy a single product end to end
        When I search for "shoe"
        And I add the first result to the cart
        Then the cart badge shows 1
        When I open the cart
        Then the cart has 1 line item
        When I place the order
        Then the order is confirmed
    
    @smoke
    Scenario:  Cart Badge reflects the number of items added
        When I search for "headphones"
        And I add the first result to the cart
        Then the cart badge shows 1
        Then I go to the product page
        And I search for "shoe"
        And I add the first result to the cart
        Then the cart badge shows 2
    
    @regression
    Scenario: Buy across several products (<product>) via one Scenario Outline
        When I search for "<product>"
        And I add the first result to the cart
        Then the cart badge shows <count>
        When I open the cart
        Then the cart has <count> line items
        When I place the order
        Then the order is confirmed

        Examples:
            | product       | count |
            | headphones    | 1     |
            | shoes         | 1     |
            | lamp          | 1     |

    @regression
    Scenario: A fresh cart is empty(0 line items) - valid empty state -checking count <count>
        When I open the cart
        Then the cart has 0 line items

        Examples:
            |   count   |
            |   1       |
            |   2       |
            |   3       |

    @negative
    Scenario: Add ivalid product to cart
        When I search for "errorProduct"
        And I try to add first product in the list - it is giving error