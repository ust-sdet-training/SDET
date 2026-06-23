import { test, expect } from "@playwright/test";
import { HomePage } from "../pages/Homepage";

test("Invalid Search", async ({ page })=> {

    const home = new HomePage(page);

    await home.goto();

    await home.searchBox().fill("xyz123456");

    await home.searchBox().press("Enter");
 await Promise.all([
        page.waitForResponse(response =>
            response.status() === 200 &&
            response.url().includes("search")
        ),
        home.searchBox().press("Enter")
    ]);

    await expect(
        home.searchBox()
    ).toHaveValue("xyz123456");
});