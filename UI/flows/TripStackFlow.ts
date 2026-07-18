import { Page } from "@playwright/test";

import { BookingData } from "../test-data/BookingData";

import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { FlightResultPage } from "../pages/FlightResultPage";
import { SeatPage } from "../pages/SeatPage";
import { PassengerPage } from "../pages/PassengerPage";
import { PaymentPage } from "../pages/PaymentPage";
import { BookingPage } from "../pages/BookingPage";
import { MyTripsPage } from "../pages/MyTripsPage";

export class TripStackFlow {

    readonly login: LoginPage;
    readonly home: HomePage;
    readonly result: FlightResultPage;
    readonly seat: SeatPage;
    readonly passenger: PassengerPage;
    readonly payment: PaymentPage;
    readonly booking: BookingPage;
    readonly myTrips: MyTripsPage;

    constructor(private page: Page) {

        this.login = new LoginPage(page);
        this.home = new HomePage(page);
        this.result = new FlightResultPage(page);
        this.seat = new SeatPage(page);
        this.passenger = new PassengerPage(page);
        this.payment = new PaymentPage(page);
        this.booking = new BookingPage(page);
        this.myTrips = new MyTripsPage(page);

    }

    // ======================================================
    // Login once
    // ======================================================

    async loginToApplication() {

        await this.login.navigateToLogin();

        await this.login.login(
            BookingData.EMAIL,
            BookingData.PASSWORD
        );

    }

    // ======================================================
    // Reusable booking method
    // ======================================================

    async bookJourney(
    source: string,
    destination: string,
    travelDays: number,
    bookingNumber: number
): Promise<string> {

    await this.home.searchFlight(
        source,
        destination,
        travelDays
    );

    await this.result.chooseFirstFlight();

    const selectedSeat =
        await this.seat.chooseSeatAndContinue();

    if (bookingNumber === 1)
        BookingData.firstSeat = selectedSeat;
    else
        BookingData.secondSeat = selectedSeat;

    await this.passenger.completePassengerForm(
        selectedSeat,
        BookingData.FIRST_NAME,
        BookingData.LAST_NAME,
        BookingData.AGE,
        BookingData.GENDER,
        BookingData.EMAIL,
        BookingData.PHONE
    );

    await this.payment.completePayment(
        BookingData.CARD_NAME,
        BookingData.CARD_NUMBER,
        BookingData.CARD_EXPIRY,
        BookingData.CARD_CVV
    );

    await this.booking.verifyBookingSuccess();

    await this.booking.verifyPNRPrefix(
        BookingData.PNR_PREFIX
    );

    return await this.booking.getPNR();

}

    // ======================================================
    // Existing single booking flow
    // ======================================================

    async completeBooking() {

        await this.loginToApplication();

       const pnr = await this.bookJourney(
        BookingData.FIRST_SOURCE,
        BookingData.FIRST_DESTINATION,
        BookingData.FIRST_TRAVEL_AFTER_DAYS,
        1
);

        BookingData.firstPNR = pnr;

    }

    // ======================================================
    // NEW - Two consecutive bookings
    // ======================================================

    async completeTwoBookings() {

        await this.loginToApplication();

        const firstPNR = await this.bookJourney(
            BookingData.FIRST_SOURCE,
            BookingData.FIRST_DESTINATION,
            BookingData.FIRST_TRAVEL_AFTER_DAYS,
            1
);

        BookingData.firstPNR = firstPNR;

        // Return to home page
        await this.booking.goHome();

        const secondPNR = await this.bookJourney(
            BookingData.SECOND_SOURCE,
            BookingData.SECOND_DESTINATION,
            BookingData.SECOND_TRAVEL_AFTER_DAYS,
            2
);

        BookingData.secondPNR = secondPNR;

    }
    async completeSeatHoldExpiryBooking() {

    await this.loginToApplication();

    await this.home.searchFlight(
        BookingData.FIRST_SOURCE,
        BookingData.FIRST_DESTINATION,
        BookingData.FIRST_TRAVEL_AFTER_DAYS
    );

    await this.result.chooseFirstFlight();

    const seat =
        await this.seat.chooseSeatAndContinue();

    await this.passenger.completePassengerForm(
        seat,
        BookingData.FIRST_NAME,
        BookingData.LAST_NAME,
        BookingData.AGE,
        BookingData.GENDER,
        BookingData.EMAIL,
        BookingData.PHONE
    );

   const result = await this.payment.completeExpiredPayment(
    BookingData.CARD_NAME,
    BookingData.CARD_NUMBER,
    BookingData.CARD_EXPIRY,
    BookingData.CARD_CVV
);

console.log(result);

}

}