Feature: End to End Checkout Flow

    As a shopper
    I want to search the catalog, add a product to my cart and complete checkout
    So that I can buy what I came for and the team can prove that journey works on every build

    Background:
        Given I open the catalog page


    @smoke
    Scenario: Buy Headphones end to end

        When I search for "Headphones"
        And I open the first product
        And I add the product to my cart
        And I open the cart
        And I proceed to checkout
        And I place the order



    @regression
    Scenario Outline: Buy "<product>" end to end

        When I search for "<product>"
        And I open the first product
        And I add the product to my cart
        And I open the cart
        And I proceed to checkout
        And I place the order


        Examples:
            | product    |
            | Headphones |
            | Shoes      |
            | Lamp       |


    @negative
    Scenario: Search an unavailable product

        When I search for "InvalidProduct"

        Then no products should be displayed