import { expect, test } from "../fixtures/diagnostic.fixture";
import { redactForLog } from "../src/logging/logger";

// Read API URL and authentication token from environment variables
const API_BASE_URL = process.env.API_BASE_URL || "http://localhost:4000";
const AUTH_TOKEN = process.env.AUTH_TOKEN || "demo-token-1-customer";

const authHeaders = {
  Authorization: `Bearer ${AUTH_TOKEN}`,
};

test.describe("Week05 Gate05 Diagnostics and Logging", () => {
  test("records a correlated checkout diagnostic trail", async ({
    correlationId,
    log,
    request,
  }) => {
    // Generate a unique cart session for this test execution
    const cartSession = `w5d2-diagnostics-${Date.now()}`;

     // Add session ID and correlation ID to every API request
    const headers = {
      ...authHeaders,
      "X-Cart-Session": cartSession,
      "x-correlation-id": correlationId,
    };

    log.info("checkout journey started", {
      cartSession,
    });

    const addStart = Date.now();
// Add a product to the shopping cart
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

    // Log API response details and execution time
    log.info("cart item added", {
      cartSession,
      durationMs: Date.now() - addStart,
      httpStatus: addItem.status(),
      productId: 101,
    });

     // Verify item was added successfully
    expect(addItem.status()).toBe(201);

    const cart = await request.get(`${API_BASE_URL}/api/cart`, {
      headers,
    });

    const cartBody = await cart.json();

     // Log cart details for diagnostics
    log.debug("cart snapshot loaded", {
      cartSession,
      httpStatus: cart.status(),
      itemCount: cartBody.items.length,
      total: cartBody.total,
    });

// Validate cart details
    expect(cart.status()).toBe(200);
    expect(cartBody.items).toHaveLength(1);
    expect(cartBody.total).toBeGreaterThan(0);

    const orderStart = Date.now();

  // Place an order using the cart
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

// Log order details and response time
    log.info("order placed", {
      cartSession,
      durationMs: Date.now() - orderStart,
      httpStatus: order.status(),
      orderId: orderBody.id,
      orderNumber: orderBody.orderNumber,
      total: orderBody.total,
    });

 // Validate successful order creation
    expect(order.status()).toBe(201);
    expect(orderBody.orderNumber).toMatch(/^ORD-/);
    expect(orderBody.status).toBe("Confirmed");
    expect(orderBody.total).toBe(cartBody.total + 99);
  });

  test("redacts sensitive fields in structured logs", async ({ log }) => {
    const payload = {
      cardNumber: "4111111111111111",
      headers: {
        authorization: "Bearer should-not-leak",
      },
      orderId: "ORD-LOG-1001",
      token: "secret-token",
    };

    log.info("payment payload prepared", payload);

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