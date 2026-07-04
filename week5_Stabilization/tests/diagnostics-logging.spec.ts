import { expect, test } from "../fixtures/test";

const posApiUrl = process.env.POS_API_URL || "http://localhost:4000";
const authHeaders = {
  Authorization: "Bearer demo-token-1-customer"
};

test.describe("Week 5 diagnostics and logging", () => {
  test("records a correlated cart diagnostic trail", async ({ correlationId, log, request }) => {
    const cartSession = `w5d5-diag-${Date.now()}`;

    const headers = {
      ...authHeaders,
      "x-correlation-id": correlationId,
      "X-Cart-Session": cartSession,
      "Content-Type": "application/json"
    };

    log.info("diagnostic journey started", { cartSession });

    const addItem = await request.post(`${posApiUrl}/api/cart/items`, {
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
      status: addItem.status()
    });

    expect(addItem.status()).toBe(201);

    const cart = await request.get(`${posApiUrl}/api/cart`, { headers });
    const cartBody = await cart.json();

    log.debug("cart snapshot loaded", {
      cartSession,
      itemCount: cartBody.items?.length ?? 0,
      total: cartBody.total
    });

    expect(cart.status()).toBe(200);
    expect(cartBody.items).toHaveLength(1);
    expect(cartBody.total).toBeGreaterThan(0);
  });
});
