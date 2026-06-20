Feature: Catalog checkout
As a shopper
I want to search the catalog, add a product to my cart, and complete checkout
So that I can buy what I came for


@smoke
Scenario: Buy a single product end to end

Given the catalog is open

When I search for "headphones"
And I add the first result to the cart
Then the cart badge shows 1

When I open the cart
Then the cart has 1 line item

And I capture the cart total

When I place the order

Then the order is confirmed


@smoke
Scenario: Cart badge reflects number of items added

Given the catalog is open

When I search for "headphones"
And I add the first result to the cart

Then the cart badge shows 1


@regression
Scenario Outline: Buy "<product>" end to end

Given the catalog is open

When I search for "<product>"
And I add the first result to the cart

Then the cart badge shows <count>

When I open the cart
Then the cart has <count> line item

When I place the order

Then the order is confirmed

Examples:
| product | count |
| headphones | 1 |
| shoes | 1 |
| lamp | 1 |
| Backpack | 1 |


@regression
Scenario: Fresh cart is empty

Given the catalog is open

When I open the cart

Then the cart has 0 line item


@negative
Scenario: Demonstrate failure evidence

Given the catalog is open

When I search for "headphones"

Then the cart badge shows 2


@smoke @e2e
Scenario: Customer logs in and purchases product

Given the login page is open

When I login using "customer@example.com" and "Password@123"

Then login should succeed

When I open the catalog

And I search for "headphones"
And I add the first result to the cart

Then the cart badge shows 1

When I open the cart

Then the cart has 1 line item

And I capture the cart total

When I place the order

Then the order is confirmed