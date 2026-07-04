import process from "node:process";
import { expect, test } from "../../fixtures/app.fixture";

const API_BASE_URL = process.env.API_BASE_URL || "http://localhost:4000";
const AUTH_TOKEN = process.env.AUTH_TOKEN || "demo-token-1-customer";

const headers = {
  Authorization: `Bearer ${AUTH_TOKEN}`,
};

test.describe("Artifact Management", () => {

  test("captures cart evidence without attaching it on a passing test",
    async ({ evidence, page, request }) => {

      // Open the debug lab page to inspect the cart flow.
      await page.goto("/debug-lab");

      // Confirm that the debug lab page is loaded.
      await expect(page.getByRole("heading", { name: "Debug Lab" })).toBeVisible();

      // Add a sample product to the cart through the API.
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

      // Fetch the current cart state after adding the item.
      const cart = await request.get(
        `${API_BASE_URL}/api/cart`,
        { headers }
      );

      // Save the cart payload as evidence for the test run.
      evidence.cartResponse = await cart.json();

      // Verify the add and fetch requests both succeeded.
      expect(addItem.status()).toBe(200);
      expect(cart.status()).toBe(200);

      // Check that the cart payload has the expected structure.
      expect(evidence.cartResponse).toMatchObject({
        items: expect.any(Array),
        total: expect.any(Number)
      });
  });

});