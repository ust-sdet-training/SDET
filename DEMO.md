Happy Path Demo

This demo shows the full happy-path scenario for the Order Management application.
It demonstrates how the APIs work together, from when a user creates an order to when it is shipped.

## Flow 1 - User Actions

In this flow, a user does the following:

1.
Logs in to the application.
2.
Clears the existing cart.
3.
Adds a product to the cart.
4.
Places an order.

## Flow 2 - Order Processing

Once the order is placed, the operations team handles it through secure APIs:

1.
Creates the order.
2.
Allocates the order.
3.
Ships the order.
4.
Fetches the order details and checks them against the database.

## Test Steps

The test follows these steps:

1.
Gets an access token using `getAccessToken()`.
2.
Logs in using `/api/login`.
3.
Clears the cart using `/api/cart`.
4.
Adds a product using `/api/cart/items`.
5.
Places the order using `/api/orders`.
6.
Creates the order using `/api/secure/orders`.
7.
Allocates the order using `/api/secure/orders/{id}/allocate`.
8.
Ships the order using `/api/secure/orders/{id}/ship`.
9.
Fetches the order details and compares the order data with the database using `DbSupport`.

## What is Verified?


- User login is successful.

- Cart operations work correctly.

- Order creation, allocation, and shipment are successful.

- API responses have the right data.

- Order details in the database match the data from the API.


This test helps ensure that the full order process works correctly from the user’s side and the operations team’s side.
