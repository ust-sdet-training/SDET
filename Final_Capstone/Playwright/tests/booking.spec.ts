import { test, expect } from "../fixtures/fixture";

import { LoginPage } from "../pages/LoginPage";
import { FlightSearchPage } from "../pages/FlightSearchPage";
import { BookingPage } from "../pages/BookingPage";

import { config } from "../data/config";
import { journey } from "../data/journeyData";
import { DateUtils } from "../utils/DateUtil";
import { logger } from "../logger/logger";

test.describe("Flight Booking", () => {

    test("Verify user can book the first flight", async ({ page }) => {

        const loginPage = new LoginPage(page);
        const flightSearchPage = new FlightSearchPage(page);
        const bookingPage = new BookingPage(page);

        logger.info("Starting flight booking test");
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

        await expect(page).toHaveURL(/FL-BOMMAA-51/i);
        await expect(page.getByText('I IndiGo 6E-494 BOM 05:57 → MAA 08:18 ₹6,123 Economy · incl. tax')).toBeVisible();
        await expect(page.getByRole('heading', { name: 'Choose your seats' })).toBeVisible();
        logger.info("Flight booking test completed");
    });

});