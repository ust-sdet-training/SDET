import { test } from "@playwright/test";
import { ShopFlow } from "../flows/Shopflow";

test("search to cart flow", async ({ page }) => {
  await page.goto("/catalog");

  const shopFlow = new ShopFlow(page);

  await shopFlow.searchAndAddToCart("Running Shoes");
});