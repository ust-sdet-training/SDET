import {test, expect} from "../src/fixtures/logging-fixture"
import { redactForLog } from "../src/logger";

//Implementation of checkout journey with logs
test("checkout journey with logs", async ({page,log,correlationId}) => {

  log.info("checkout journey starts", {correlationId});

  await page.goto("/login");

  log.info("login page opened");

  await page.getByLabel("Email").fill("customer@example.com");
  await page.getByLabel("Password").fill("Password@123");

  log.info("credentials entered");

  await page.getByRole("button", {name: "Sign in"}).click();

  await expect(page).toHaveURL(/home/);

  log.info("login successful");

  await page.goto("/catalog");

  log.info("catalog opened");

  await page.getByRole("link", {name: "View Running Shoes"}).click();

  log.info("product opened", {product: "Running Shoes"});

  await page.getByRole("button", {name: "Add to cart"}).click();

  log.info("item added", {productId: 101,quantity: 1});

  await page.getByRole("button", {name: "Proceed to checkout"}).click();

  log.info("checkout opened");

  await page.locator("#coupon-code").fill("UST10");

  log.info("coupon entered", {coupon: "UST10"});

  await expect(page.locator(".order-summary")).toContainText("-Rs. 450");

  log.info("discount applied", {discount: 450});

  await page.getByRole("button", {name: "Place order"}).click();

  await expect(page.getByRole("button", {name: "View orders"})).toBeVisible();

  log.info("order placed successfully");
});