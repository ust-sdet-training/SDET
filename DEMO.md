# Happy Path Demo

This demo covers the complete happy-path scenario for the Order Management application and shows how the APIs work together from order creation to shipment.

## Flow 1 - User Actions

In this flow, a user performs the following actions:

1. Login to the application.
2. Clear the existing cart.
3. Add a product to the cart.
4. Place an order.

## Flow 2 - Order Processing

After the order is placed, the operations team processes it through secure APIs:

1. Create the order.
2. Allocate the order.
3. Ship the order.
4. Fetch the order details and verify them with the database.

## Test Steps

The test executes the following steps:

1. Get an access token using `getAccessToken()`.
2. Login using `/api/login`.
3. Clear the cart using `/api/cart`.
4. Add a product using `/api/cart/items`.
5. Place the order using `/api/orders`.
6. Create the order using `/api/secure/orders`.
7. Allocate the order using `/api/secure/orders/{id}/allocate`.
8. Ship the order using `/api/secure/orders/{id}/ship`.
9. Fetch the order details and compare the order data with the database using `DbSupport`.

## What is Verified?

* User login is successful.
* Cart operations work as expected.
* Order creation, allocation, and shipment are successful.
* API responses contain the expected data.
* Order details in the database match the API response.

This test gives confidence that the complete order lifecycle is working correctly from both the user and operations perspectives.
