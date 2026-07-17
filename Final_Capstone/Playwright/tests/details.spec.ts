import { test, expect } from "../fixtures/fixture";

import { LoginPage } from "../pages/LoginPage";
import { FlightSearchPage } from "../pages/FlightSearchPage";
import { BookingPage } from "../pages/BookingPage";
import { CabinPage } from "../pages/CabinPage";
import { DetailsPage } from "../pages/DetailsPage";

import { config } from "../data/config";

import { journey } from "../data/journeyData";
import { passenger } from "../data/passengerData";
import { DateUtils } from "../utils/DateUtil";
import { logger } from "../logger/logger";

test.describe("Passenger Details", () => {

    test("Verify user can enter passenger details successfully", async ({ page }) => {

        const loginPage = new LoginPage(page);
        const flightSearchPage = new FlightSearchPage(page);
        const bookingPage = new BookingPage(page);
        const cabinPage = new CabinPage(page);
        const detailsPage = new DetailsPage(page);

        logger.info("Starting passenger details test");
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

        await cabinPage.selectSeatAndContinue();

        await detailsPage.enterPassengerDetails(
            passenger.firstName,
            passenger.lastName,
            passenger.age,
            passenger.gender,
            passenger.email,
            passenger.phone
        );

        await expect(page).toHaveURL(/payment/i);
        logger.info("Passenger details test completed");
    });

});