import { expect, test } from "../fixtures/test";

const POS_API_URL = process.env.POS_API_URL || "http://localhost:4000";
const AUTH_HEADERS = {
  Authorization: "Bearer demo-token-1-customer"
};

test.describe("Week 5 diagnostics and logging", () => {
  test("records a correlated cart diagnostic trail", async ({ correlationId, log, request }) => {
    const cartSession = `w5d5-diag-${Date.now()}`;

    const headers = {
      ...AUTH_HEADERS,
      "x-correlation-id": correlationId,
      "X-Cart-Session": cartSession,
      "Content-Type": "application/json"
    };

    log.info("diagnostic journey started", { cartSession });

    const addItemResponse = await request.post(`${POS_API_URL}/api/cart/items`, {
      headers,
      data: {
        productId: 101,
        quantity: 1,
        size: "UK 9",
        color: "Black",
        fulfillment: "Home delivery"
      }
    });

    log.info("cart item requested", {
      cartSession,
      status: addItemResponse.status()
    });

    expect(addItemResponse.status()).toBe(201);

    const cartResponse = await request.get(`${POS_API_URL}/api/cart`, { headers });
    const cart = await cartResponse.json();

    log.debug("cart snapshot loaded", {
      cartSession,
      itemCount: cart.items?.length ?? 0,
      total: cart.total
    });

    expect(cartResponse.status()).toBe(200);
    expect(cart.items).toHaveLength(1);
    expect(cart.total).toBeGreaterThan(0);
  });
});