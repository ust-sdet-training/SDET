import { test } from "../fixtures/baseFixtures";

import { users } from "../test-data/user";
import { flights } from "../test-data/flights";

import { getFutureDate } from "../utils/dateUtil";
import { seats } from "../test-data/seats";
import { passenger } from "../test-data/passenger";
import { payment } from "../test-data/payment";

test("User should successfully book a flight", async ({page, loginPage, homePage, resultPage, seatPage, passengerPage, paymentPage, bookingConfirmationPage
}) => {

    await page.goto("/");
    await loginPage.login(
        users.validUser.email,
        users.validUser.password
    );

    await homePage.searchFlight(
        flights.oneWay.from,
        flights.oneWay.to,
        getFutureDate(flights.oneWay.daysFromToday)
    );

    await resultPage.verifySearchResult();
    await resultPage.bookFlight();
    await seatPage.selectSeat(seats.preferredSeat);
    await seatPage.continueBooking();
    await passengerPage.fillPassengerDetails(
        passenger
    );

    await passengerPage.continueBooking();

    await paymentPage.enterPaymentDetails(payment);
    await paymentPage.completePayment();
    
    const pnrNumber = await bookingConfirmationPage.getPNRNumber();
    console.log(`PNR Number: ${pnrNumber}`);
    await bookingConfirmationPage.clickViewMyTrip();
});