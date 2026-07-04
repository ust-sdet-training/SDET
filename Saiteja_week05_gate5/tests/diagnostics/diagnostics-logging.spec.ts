import { expect, test } from "../../fixtures/diagnostic.fixture";
import { redactForLog } from "../../src/logging/logger";

const API_BASE_URL = process.env.API_BASE_URL || "http://localhost:4000";
const AUTH_TOKEN = process.env.AUTH_TOKEN || "demo-token-1-customer";

const authHeaders = {
  Authorization: `Bearer ${AUTH_TOKEN}`,
};

test.describe("Diagnostics and Logging", () => {
  test("records a correlated checkout diagnostic trail", async ({
    correlationId,
    log,
    request,
  }) => {
    // Create a unique cart session so the logs can be traced easily.
    const cartSession = `w5d2-diagnostics-${Date.now()}`;

    // Build request headers with the session and correlation ID.
    const headers = {
      ...authHeaders,
      "X-Cart-Session": cartSession,
      "x-correlation-id": correlationId,
    };

    // Log the start of the checkout journey for traceability.
    log.info("checkout journey started", {
      cartSession,
    });

    // Measure how long the cart add operation takes.
    const addStart = Date.now();

    // Add an item to the cart through the API.
    const addItem = await request.post(`${API_BASE_URL}/api/cart/items`, {
      headers,
      data: {
        productId: 101,
        quantity: 1,
        size: "UK 9",
        color: "Black",
        fulfillment: "Home delivery",
      },
    });

    // Record the cart add event with the timing and status.
    log.info("cart item added", {
      cartSession,
      durationMs: Date.now() - addStart,
      httpStatus: addItem.status(),
      productId: 101,
    });

    // Confirm the item was accepted by the API.
    expect(addItem.status()).toBe(201);

    // Fetch the cart contents after the add request.
    const cart = await request.get(`${API_BASE_URL}/api/cart`, {
      headers,
    });

    const cartBody = await cart.json();

    // Log the cart snapshot for debugging and correlation.
    log.debug("cart snapshot loaded", {
      cartSession,
      httpStatus: cart.status(),
      itemCount: cartBody.items.length,
      total: cartBody.total,
    });

    // Verify the cart is visible and contains the expected item.
    expect(cart.status()).toBe(200);
    expect(cartBody.items).toHaveLength(1);
    expect(cartBody.total).toBeGreaterThan(0);

    // Measure the order creation step before submitting it.
    const orderStart = Date.now();

    // Place an order using the current cart contents.
    const order = await request.post(`${API_BASE_URL}/api/orders`, {
      headers,
      data: {
        address: "Block A, UST Campus, Trivandrum",
        coupon: "WELCOME",
        deliverySlot: "Tomorrow 10 AM - 1 PM",
        discount: 0,
        paymentMethod: "UPI",
        shipping: 99,
      },
    });

    const orderBody = await order.json();

    // Record the completed order with the returned metadata.
    log.info("order placed", {
      cartSession,
      durationMs: Date.now() - orderStart,
      httpStatus: order.status(),
      orderId: orderBody.id,
      orderNumber: orderBody.orderNumber,
      total: orderBody.total,
    });

    // Confirm the order was accepted and the totals line up.
    expect(order.status()).toBe(201);
    expect(orderBody.orderNumber).toMatch(/^ORD-/);
    expect(orderBody.status).toBe("Confirmed");
    expect(orderBody.total).toBe(cartBody.total + 99);
  });

  test("redacts sensitive fields in structured logs", async ({ log }) => {
    // Build a payload that contains sensitive-looking values.
    const payload = {
      cardNumber: "4111111111111111",
      headers: {
        authorization: "Bearer should-not-leak",
      },
      orderId: "ORD-LOG-1001",
      token: "secret-token",
    };

    // Log the payload before redaction is applied.
    log.info("payment payload prepared", payload);

    // Verify that sensitive values are masked in the logged output.
    expect(redactForLog(payload)).toEqual({
      cardNumber: "[REDACTED]",
      headers: {
        authorization: "[REDACTED]",
      },
      orderId: "ORD-LOG-1001",
      token: "[REDACTED]",
    });
  });
});