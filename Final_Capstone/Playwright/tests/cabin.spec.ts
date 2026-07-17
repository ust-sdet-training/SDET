import { test, expect } from "../fixtures/fixture";
import { LoginPage } from "../pages/LoginPage";
import { FlightSearchPage } from "../pages/FlightSearchPage";
import { BookingPage } from "../pages/BookingPage";
import { CabinPage } from "../pages/CabinPage";

import { config } from "../data/config";
import { journey } from "../data/journeyData";
import { DateUtils } from "../utils/DateUtil";
import { logger } from "../logger/logger";

test.describe("Cabin Seat Selection", () => {

    test("Verify user can select a seat and continue", async ({ page }) => {

        const loginPage = new LoginPage(page);
        const flightSearchPage = new FlightSearchPage(page);
        const bookingPage = new BookingPage(page);
        const cabinPage = new CabinPage(page);

        logger.info("Starting cabin seat selection test");
        await loginPage.navigate(config.baseUrl);
        await loginPage.openLoginPage();
        await loginPage.login(
            config.email,
            config.password
        );

        await flightSearchPage.searchFlight(
            journey.source,
            journey.destination,
            DateUtils.getFutureDate(journey.days)
        );

        await bookingPage.bookFirstFlight();

        await expect(page.getByLabel('Seat 13F, window, available')).toBeEnabled();

        await cabinPage.selectSeatAndContinue();

        await expect(page).toHaveURL(/book/i);
        await expect(page).toHaveURL(/passenger/i);
        await expect(page.getByText("Who's travelling?")).toBeVisible;
        await expect(page.getByText("Traveller 1")).toBeVisible;
        logger.info("Cabin seat selection test completed");
    });

});