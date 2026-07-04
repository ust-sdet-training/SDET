import { expect, test } from "../fixtures/evidence";
 
 
test.describe("flaky in Login func", () => {
 
    // simple: simulate flaky login click timing
    test("flaky test", async ({ page, evidence }) => {
        await page.goto("/login");
        await page.getByRole("textbox", { name: "Email" }).fill("customer@example.com");
        await page.getByRole("textbox", { name: "Password" }).fill("Password@123");
        await page.waitForTimeout(1000);

        evidence.cartResponse = {
            page: "/login",
            action: "clicked sign in with 100 ms timeout",
        };
        evidence.diagnosis = "Flaky login: click can happen before the Sign in button is ready.";

        await page.getByRole("button", { name: "Sign in" }).click({timeout: 100});
    });
   
    // simple: wait then click to fix flaky login
    test("flaky test - fix", async ({ page, evidence }) => {
        await page.goto("/login");
        await page.getByRole("textbox", { name: "Email" }).fill("customer@example.com");
        await page.getByRole("textbox", { name: "Password" }).fill("Password@123");

        evidence.cartResponse = {
            page: "/login",
            action: "waited for Sign in button before clicking",
        };
        evidence.diagnosis = "Fixed login: web-first expect waits for the Sign in button.";

        await expect(page.getByRole("button", { name: "Sign in" })).toBeEnabled();
        await page.getByRole("button", { name: "Sign in" }).click();
    });
 
});
 
 
