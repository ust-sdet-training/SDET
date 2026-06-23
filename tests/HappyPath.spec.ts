import { test, expect } from "@playwright/test";
import { HomePage } from "../pages/Homepage";
test("Search Product", async ({ page }) =>{

    await page.goto("https://www.shoppersstop.com");

    const home = new HomePage(page);

    await home.searchBox().fill("Shoes");

    await Promise.all([
        page.waitForResponse(response =>
            response.status() === 200 &&
            response.url().includes("search")
        ),
        home.searchBox().press("Enter")
    ]);
    await expect(
        home.searchBox()
    ).toHaveValue("Shoes");
});

