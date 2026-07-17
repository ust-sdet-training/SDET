import { test, expect } from "../fixtures/fixture";
import { LoginPage } from "../pages/LoginPage";
import { config } from "../data/config";
import { logger } from "../logger/logger";


test.describe("TripStack Login", () => {

    test("Verify user can login successfully", async ({ page }) => {

        const loginPage = new LoginPage(page);

        logger.info("Starting login test");

        await loginPage.navigate(config.baseUrl);

        await expect(page).toHaveURL(config.baseUrl);
        await expect(page.getByRole("link", { name: "TripStack" })).toBeVisible();

        await loginPage.openLoginPage();

        await loginPage.login(
            config.email,
            config.password
        );

        await expect(page).not.toHaveURL("/login");

        await expect(page.getByRole("link", { name: /my trips/i })).toBeVisible();
        await expect(page.getByRole('heading', { name: 'Book flights & buses across India'})).toBeVisible();
        logger.info("Login test completed successfully");
    });

});