import { test, expect } from "../fixtures/diagnostic-test";
import { redactForLog } from "../src/logger";

const posApiUrl = process.env.POS_API_URL || "http://localhost:4000";

const authHeaders = {
  Authorization: "Bearer demo-token-1-customer"
};

test.describe("Week 5 Day 2 - Diagnostics and Logging", () => {
  test("record a correlated checkout diagnostic trail", async ({
    correlationId,
    log,
    request
  }) => {
    // Use a unique cart session for each execution
    const cartSession = `w5d2-diagnostic-${Date.now()}`;

    const headers = {
      ...authHeaders,
      "X-Cart-Session": cartSession,
      "x-correlation-id": correlationId
    };

    log.info("checkout journey started", { cartSession });

    const addStart = Date.now();

    const addItem = await request.post(`${posApiUrl}/api/cart/items`, {
      headers,
      data: {
        productId: 101,
        quantity: 1,
        size: "UK 9",
        color: "Black",
        fulfilment: "Home delivery"
      }
    });

    expect(addItem.status()).toBe(201);

    // Log how long adding the item took
    log.info("cart item added", {
      cartSession,
      durationMs: Date.now() - addStart,
      httpStatus: addItem.status(),
      productId: 101
    });

    const cart = await request.get(`${posApiUrl}/api/cart`, {
      headers
    });

    expect(cart.status()).toBe(200);

    const cartBody = await cart.json();

    log.debug("cart snapshot loaded", {
      cartSession,
      httpStatus: cart.status(),
      itemCount: cartBody.items.length,
      total: cartBody.total
    });

    expect(cartBody.items).toHaveLength(1);
    expect(cartBody.total).toBeGreaterThan(0);

    const orderStart = Date.now();

    const order = await request.post(`${posApiUrl}/api/orders`, {
      headers,
      data: {
        address: "Block A, Ust Campus, Trivandrum",
        coupon: "WELCOME",
        deliverySlot: "Tomorrow 10 AM - 1 PM",
        discount: 0,
        paymentMethod: "UPI",
        shipping: 99
      }
    });

    expect(order.status()).toBe(201);

    const orderBody = await order.json();

    // Capture useful order details for debugging
    log.info("order placed", {
      cartSession,
      durationMs: Date.now() - orderStart,
      httpStatus: order.status(),
      orderId: orderBody.id,
      orderNumber: orderBody.orderNumber,
      total: orderBody.total
    });

    expect(orderBody.orderNumber).toMatch(/^ORD-/);
    expect(orderBody.status).toBe("Confirmed");
    expect(orderBody.total).toBe(cartBody.total + 99);
  });

  test("redact sensitive fields in structured logs", async ({ log }) => {
    const payload = {
      cardNumber: "41111111111111111",
      headers: {
        Authorization: "Bearer should-not-leak"
      },
      orderId: "ORD-LOG-1001",
      token: "secret-token"
    };

    log.info("payment payload prepared", payload);

    // Verify sensitive data is hidden before logging
    expect(redactForLog(payload)).toEqual({
      cardNumber: "[REDACTED]",
      headers: {
        Authorization: "[REDACTED]"
      },
      orderId: "ORD-LOG-1001",
      token: "[REDACTED]"
    });
  });
});