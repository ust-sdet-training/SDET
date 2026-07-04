import { expect, test } from "../fixtures/app.fixture";

test("Login", async ({ page, log }) => {

    await page.goto("/login");

    await page.getByLabel("Email").fill("customer@example.com");
    await page.getByLabel("Password").fill("Password@123");
    await page.getByLabel("Country").selectOption({ label: "India" });
    log.error("flacky test")
    await page.getByRole("button", { name: "Sign in" }).click();

});