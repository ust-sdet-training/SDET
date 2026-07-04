import { expect, test } from "../fixtures/diagnostics";
import { redactForLog } from "../src/logger.ts";

const BASE_URL = process.env.API_BASE_URL || "http://localhost:4000";
const TOKEN = process.env.AUTH_TOKEN || "demo-token-1-customer";

const requestHeaders = {
  Authorization: `Bearer ${TOKEN}`,
};

test.describe("Week 5 Day 2 - Diagnostics and Logging", () => {

  test("capture checkout flow with logs", async ({
    correlationId,
    log,
    request,
  }) => {

    // Create a unique cart session so every execution is independent
    const cartId = `checkout-${Date.now()}`;

    const headers = {
      ...requestHeaders,
      "X-Cart-Session": cartId,
      "x-correlation-id": correlationId,
    };

    log.info("Starting checkout flow", {
      cartId,
    });

    // Measure how long it takes to add an item to the cart
    const addItemStart = Date.now();

    const addToCartResponse = await request.post(
      `${BASE_URL}/api/cart/items`,
      {
        headers,
        data: {
          productId: 101,
          quantity: 1,
          size: "UK 9",
          color: "Black",
          fulfillment: "Home delivery",
        },
      }
    );

    //Get the result of adding an item to the cart
    log.info("Item added to cart", {
      cartId,
      responseTime: Date.now() - addItemStart,
      statusCode: addToCartResponse.status(),
      productId: 101,
    });

    expect(addToCartResponse.status()).toBe(201);

    // Get the latest cart details
    const cartResponse = await request.get(`${BASE_URL}/api/cart`, {
      headers,
    });

    // Get the cart details for debugging purposes
    const cartDetails = await cartResponse.json();

    log.debug("Cart details received", {
      cartId,
      statusCode: cartResponse.status(),
      totalItems: cartDetails.items.length,
      totalAmount: cartDetails.total,
    });

    //Assert the cart response is successful and contains the expected data
    expect(cartResponse.status()).toBe(200);
    expect(cartDetails.items).toHaveLength(1);
    expect(cartDetails.total).toBeGreaterThan(0);

    // Start measuring order creation time
    const orderStart = Date.now();

    // Place the order and log the order details
    const orderResponse = await request.post(`${BASE_URL}/api/orders`, {
      headers,
      data: {
        address: "UST Office, Trivandrum",
        coupon: "WELCOME",
        deliverySlot: "Tomorrow 10 AM - 1 PM",
        discount: 0,
        paymentMethod: "UPI",
        shipping: 99,
      },
    });

    const orderDetails = await orderResponse.json();

    //Get the order details for debugging purposes
    log.info("Order created successfully", {
      cartId,
      responseTime: Date.now() - orderStart,
      statusCode: orderResponse.status(),
      orderId: orderDetails.id,
      orderNumber: orderDetails.orderNumber,
      total: orderDetails.total,
    });

    // Validate the order creation response and details
    expect(orderResponse.status()).toBe(201);
    expect(orderDetails.status).toBe("Confirmed");
    expect(orderDetails.total).toBe(cartDetails.total + 99);
  });

});