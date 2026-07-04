import { test, expect } from "../fixtures/artifact.fixture";

const API_URL = process.env.API_BASE_URL || "http://localhost:4000";

const headers = {
  Authorization: "Bearer demo-token-1-customer",
};

test.describe("Artifact Management", () => {
  test("Collect checkout evidence successfully", async ({ request, evidence }) => {

    // Create a unique cart session
    const cartSession = `cart-${Date.now()}`;

    // Fetch cart details
    const cart = await request.get(`${API_URL}/api/cart`, {
      headers: {
        ...headers,
        "X-Cart-Session": cartSession,
      },
    });

    // Verify API call
    expect(cart.status()).toBe(200);

    // Read response
    const cartBody = await cart.json();

    // Save evidence
    evidence.cartResponse = cartBody;

    evidence.diagnosis =
      "Cart response captured successfully for reporting.";

    // Verify cart structure
    expect(cartBody.total).toEqual(expect.any(Number));
    expect(cartBody.total).toBeGreaterThanOrEqual(0);

    expect(Array.isArray(cartBody.items)).toBeTruthy();
  });
});