import process from "node:process";
import { expect, test } from "../fixtures/app.fixture";

// Reading enviromnmental variables
const API_BASE_URL = process.env.API_BASE_URL || "http://localhost:4000";
const AUTH_TOKEN = process.env.AUTH_TOKEN || "demo-token-1-customer";

const headers = {
  Authorization: `Bearer ${AUTH_TOKEN}`,
};

test.describe("Week05 Gate05 - Artifact Management", () => {

  test("captures cart evidence without attaching it on a passing test",
    async ({ evidence, page, request }) => {

      // Open the Debug Lab page
      await page.goto("/debug-lab");

      await expect(page.getByRole("heading", { name: "Debug Lab" })).toBeVisible();

      // Add a product to the cart using the API
      const addItem = await request.post(
        `${API_BASE_URL}/api/cart/items`,
        {
          headers,
          data: {
            productId: 101,
            quantity: 1,
            size: "UK 9",
            color: "Black",
            fulfillment: "Home delivery"
          }
        }
      );

      const cart = await request.get(
        `${API_BASE_URL}/api/cart`,
        { headers }
      );

      // Store the cart response as test evidence
      evidence.cartResponse = await cart.json();

      // Verify the product was added successfully
      expect(addItem.status()).toBe(201);
      // Verify the cart retrieval was successful
      expect(cart.status()).toBe(200);

      // Validate the structure of the cart response
      expect(evidence.cartResponse).toMatchObject({
        items: expect.any(Array),
        total: expect.any(Number)
      });
  });

});