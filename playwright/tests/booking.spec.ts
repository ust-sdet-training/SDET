import { test, expect } from "../src/fixtures/test";

import login from "../src/test-data/login.json";
import search from "../src/test-data/search.json";
import passenger from "../src/test-data/passenger.json";
import payment from "../src/test-data/payment.json";

test("TripStack Bus Booking", async ({ booking, evidence, page }) => {

    evidence.inputs = {
        login: {
            email: login.validUser.email,
            password: login.validUser.password
        },
        journey: {
            from: search.validSearch.from,
            to: search.validSearch.to,
            date: search.validSearch.journeyDate
        },  passenger: passenger.passenger,  payment: payment.card
    };

    evidence.expected = {  bookingStatus: "Confirmed"  };

    await booking.login(login.validUser.email, login.validUser.password
    );

    await booking.searchBus(search.validSearch.from,search.validSearch.to,search.validSearch.journeyDate);

    await booking.filterSearchResults();
    await booking.selectSeats();

    await booking.passengerInformation(passenger.passenger.firstName, passenger.passenger.lastName,passenger.passenger.age,passenger.passenger.gender,passenger.passenger.email, passenger.passenger.phoneNumber);

    await booking.checkout(payment.card.name, payment.card.number,payment.card.expiry,payment.card.cvv  );

    const bookingStatus = await booking.verifyBookingOutcome();

    evidence.expected = {bookingStatus: "Confirmed or Payment Declined"};

    evidence.actual = {  bookingStatus };

});