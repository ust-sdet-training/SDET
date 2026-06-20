Feature: Catalog checkout

    As a shipper
    i want to search for a product in catalog and add that  product to my cart and
    completete chekout so that i can buy what i come for so that team can prove the journey

    Background:
       Given the catalog is open

    @smoke
    Scenario:Buy a single product end to end
        When I search for "shoes"
        And I add the first result to the cart
        Then the cart badge shows 1
        When I open the cart
        Then the cart has 1 line item
        When I place the order
        Then the order is confirmed

    @smoke
        Scenario:Show cart count
            When I search for "shoes"
            And  I add the first result to the cart
            Then the cart badge shows 1



    @regression
    Scenario Outline:Buy "<product>" end to end
        When I search for "<product>"
        And I add the first result to the cart
        Then the cart badge shows <count>
        When I open the cart
        Then the cart has <count> line items
        When I place the order
        Then the order is confirmed

        Examples:
            | product     | count |
            | headphones  | 1     |
            | footwear    | 1     |
            | lamp        | 1     |

    @regression
    Scenario: A fresh cart is empty
        When I open the cart
        Then the cart has 0 line items

    @negative
        Scenario: A fresh cart should be empty
            When I open the cart
            Then the cart has 1 line items







