import { test, expect } from "../fixtures/fixture";

import { LoginPage } from "../pages/LoginPage";
import { FlightSearchPage } from "../pages/FlightSearchPage";
import { BookingPage } from "../pages/BookingPage";
import { CabinPage } from "../pages/CabinPage";
import { DetailsPage } from "../pages/DetailsPage";
import { CardPage } from "../pages/CardPage";

import { config } from "../data/config";

import { journey } from "../data/journeyData";
import { passenger } from "../data/passengerData";
import { card } from "../data/cardData";
import { DateUtils } from "../utils/DateUtil";
import { logger } from "../logger/logger";

test.describe("Payment", () => {

    test("Verify user can complete payment successfully", async ({ page }) => {

        const loginPage = new LoginPage(page);
        const flightSearchPage = new FlightSearchPage(page);
        const bookingPage = new BookingPage(page);
        const cabinPage = new CabinPage(page);
        const detailsPage = new DetailsPage(page);
        const paymentPage = new CardPage(page);

        logger.info("Starting payment test");
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
            "Female",
            passenger.email,
            passenger.phone
        );

        await paymentPage.makePayment(
            card.cardHolderName,
            card.cardNumber,
            card.expiry,
            card.cvv
        );

        await expect(page).toHaveURL(/ticket|confirmation|booking/i);
        logger.info("Payment test completed");
    });

});