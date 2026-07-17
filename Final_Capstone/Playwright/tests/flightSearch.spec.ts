import { test, expect } from "../fixtures/fixture";

import { LoginPage } from "../pages/LoginPage";
import { FlightSearchPage } from "../pages/FlightSearchPage";

import { config } from "../data/config";

import { journey } from "../data/journeyData";
import { DateUtils } from "../utils/DateUtil";
import { logger } from "../logger/logger";


test.describe("Flight Search", () => {

    test("Verify user can search a flight successfully", async ({ page }) => {

        const loginPage = new LoginPage(page);
        const fsp = new FlightSearchPage(page);

        logger.info("Starting flight search test");
        await loginPage.navigate(config.baseUrl);

        await loginPage.openLoginPage();

        await loginPage.login(
            config.email,
            config.password
        );

        await fsp.searchFlight(
            journey.source,
            journey.destination,
            DateUtils.getFutureDate(journey.days)
        );

        await expect(page).toHaveURL(/results/i);
        logger.info("Flight search test completed");
    });

});