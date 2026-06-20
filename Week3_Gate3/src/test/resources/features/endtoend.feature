Feature: Full search to place order flow
    I want to search for products, add products to the cart,
    and place an order to cover the full flow from search to 
    placing an order

    Background:
        Given the catalog is open
    
    @smoke
    Scenario: Buy a single product end to end
        When I search for "shoes"
        And I add the first result to the cart
        Then the cart badge shows 1
        When I open the cart
        Then the cart has 1 line item
        When I place the order
        Then the order is confirmed

    @smoke
    Scenario: Add a single product to cart and check cart badge
        When I search for "shoes"
        And I add the first result to the cart
        Then the cart badge shows 1

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

    @regression
    Scenario: A fresh cart is empty
        When I open the cart
        Then the cart has 0 line items

    @negative
    Scenario: Sort products from high to low and assert false
        When I sort products by "Price: Low to High"
        Then the products are sorted in ascending order