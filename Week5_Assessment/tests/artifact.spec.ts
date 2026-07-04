import process from "node:process";
import { expect, test } from "../fixtures/evidence";

const API_BASE_URL = process.env.API_BASE_URL || "http://localhost:4000";
const AUTH_TOKEN = process.env.AUTH_TOKEN || "demo-token-1-customer";

const headers = {
  Authorization: `Bearer ${AUTH_TOKEN}`,
};

test.describe("Week 5 Day 3 - Artifact Management", () => {

  test("captures cart evidence without attaching it on a passing test",
    async ({ evidence, page, request, log }) => {

      await page.goto("/debug-lab");
      log.info("Navigated to the debug page");

      await expect(page.getByRole("heading", { name: "Debug Lab" })).toBeVisible();
      log.info("Verified the debug page heading is visible");

      log.info("Adding an item to the cart");
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

      evidence.cartResponse = await cart.json();

      expect(addItem.status()).toBe(201);
      expect(cart.status()).toBe(200);

      expect(evidence.cartResponse).toMatchObject({
        items: expect.any(Array),
        total: expect.any(Number)
      });
  });

});