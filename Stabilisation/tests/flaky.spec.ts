import { expect, test } from "@playwright/test";
import { logger } from "../utils/logger";


test.describe("flaky login example", () => {

    test("flaky login test", async ({ page }) => {
        logger.info("Starting flaky test");
        await page.goto("/login");
        logger.info("Opened login page");
        await page.getByRole("textbox", { name: "Email" }).fill("customer@example.com");
        await page.getByRole("textbox", { name: "Password" }).fill("Password@123");
        logger.info("Filled login credentials");
        logger.info("Attempting sign-in click");
        await page.getByRole("button", { name: "Sign in" }).click({ timeout: 100 });
        logger.info("Sign-in click completed");
    });
    
    test.only("flaky login test - fix", async ({ page }) => {
        logger.info("Starting flaky test - fix");
        await page.goto("/login");
        logger.info("Opened login page");
        await page.getByRole("textbox", { name: "Email" }).fill("customer@example.com");
        await page.getByRole("textbox", { name: "Password" }).fill("Password@123");
        logger.info("Filled login credentials");
        logger.info("Checking sign-in button state");
        expect(page.getByRole("button", { name: "Sign in" })).toBeEnabled();
        logger.info("Sign-in button is enabled");
        await page.getByRole("button", { name: "Sign in" }).click();
        logger.info("Clicked sign-in button");
    });

});