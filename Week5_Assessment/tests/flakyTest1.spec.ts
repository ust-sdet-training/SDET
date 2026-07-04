import { log } from "winston";
import {test, expect} from "../fixtures/diagnostics";

const flakyTest =
  (globalThis as any).process?.env?.W5D1_SHOW_FLAKES === "true"
    ? test.describe
    : test.describe.skip;

flakyTest("Flaky Tests on demand", () => {
  test("Flaky Test 1", async ({ page, log }) => {

   //Navigate to the debug page
    await page.goto("/debug-lab", { waitUntil: "domcontentloaded" });
    log.info("Open debug page");

    // Wait until the refresh cart total button is visible/enabled
    const refresh = page.getByRole("button", { name: "Refresh cart total" });
    await expect(refresh).toBeVisible();
    await refresh.click();
    log.info("Clicked on refresh cart total button");

    //Wait until the cart total is visible and assert whether the price is correct or not
    const cartTotal = page.getByTestId("debug-cart-total");
    await expect(cartTotal).toBeVisible();
    await expect(cartTotal).toHaveText("Rs. 9,197", { timeout: 10 });
    log.info("Asserted the cart total is correct");
  });



  test("Flaky Test 2, causing flakiness after sign in", async ({ page,log }) => {

    //Navigate to the login page
    await page.goto("/login")
    log.info("Navigated to the login page");

    //Fill the login form
    await page.getByLabel("Email").fill("customer@example.com");
    await page.getByLabel("Password ").fill("Password@123");

    //Click the Sign in button and assert the URL
    await page.getByRole("button", { name: "Sign in" }).click({timeout: 100000});

    //Here the flakiness is being detected
    // The flakiness is resolved by using the wait for navigation method

  });
});