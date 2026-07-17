import { HomePage } from '../pages/HomePage';
import { SearchResultPage } from '../pages/SearchResultPage';
import { PassengerPage } from '../pages/PassengerPage';
import { PaymentPage } from '../pages/PaymentPage';
import { BookingConfirmationPage } from '../pages/BookingConfirmationPage';
import { MyTripsPage } from '../pages/MyTripsPage';

import {
    bookingData,
    passengerData
} from '../utils/testData';

export class BookingFlow {

    constructor(
        private homePage: HomePage,
        private searchResultPage: SearchResultPage,
        private passengerPage: PassengerPage,
        private paymentPage: PaymentPage,
        private bookingConfirmationPage: BookingConfirmationPage,
        private myTripsPage: MyTripsPage
    ) { }

    async searchFlight() {

        await this.homePage.verifyHomePageLoaded();

        await this.homePage.enterSource(
            bookingData.from
        );

        await this.homePage.enterDestination(
            bookingData.to
        );

        await this.homePage.clickSearch();

    }

    async selectFlightAndSeat() {

        await this.searchResultPage.verifySearchResultsLoaded();

        await this.searchResultPage.clickBook();

        const triedSeats = new Set<string>();

while (true) {

    const selectedSeat =
        await this.searchResultPage.selectSeat(triedSeats);

    console.log(`Trying seat: ${selectedSeat}`);

    // Wait briefly for the UI to react
    await this.searchResultPage.page.waitForTimeout(500);

    if (!(await this.searchResultPage.isContinueButtonEnabled())) {

        console.log(`${selectedSeat} was not accepted.`);

        triedSeats.add(selectedSeat);

        continue;
    }

    await this.searchResultPage.continueToPassengerDetails();

    if (await this.passengerPage.isPassengerPageLoaded()) {
        return;
    }

    if (await this.searchResultPage.isSeatUnavailable()) {

        triedSeats.add(selectedSeat);

        await this.searchResultPage.goBackToSeatSelection();

        continue;
    }

    throw new Error('Unexpected application state.');

}

    }

    async enterPassengerDetails() {

        await this.passengerPage.verifyPassengerPageLoaded();

        await this.passengerPage.fillPassengerDetails(
            passengerData.firstName,
            passengerData.lastName,
            passengerData.age,
            passengerData.email,
            passengerData.phone
        );

        await this.passengerPage.clickContinueToPayment();

    }

    async makePayment() {

        await this.paymentPage.verifyPaymentPageLoaded();

        await this.paymentPage.makePayment();

    }

    async verifyBookingSuccess() {

        await this.bookingConfirmationPage.verifyBookingSuccess();

        await this.bookingConfirmationPage.verifyJourneyDetails();

    }

    async openMyTrips() {

        await this.bookingConfirmationPage.clickViewMyTrips();

        await this.myTripsPage.verifyMyTripsPageLoaded();

    }

    async verifyBookingInTrips() {

        await this.myTripsPage.verifyBookingExists();

    }

    async completeBooking() {

        await this.searchFlight();

        await this.selectFlightAndSeat();

        await this.enterPassengerDetails();

        await this.makePayment();

        await this.verifyBookingSuccess();

        await this.openMyTrips();

        await this.verifyBookingInTrips();

    }

    async completeRoundTripBooking() {

        // Outbound Journey
        await this.completeBooking();

        // Return Journey

        await this.homePage.page.goto('/');

        await this.homePage.verifyHomePageLoaded();

        await this.homePage.enterSource(
            bookingData.to
        );

        await this.homePage.enterDestination(
            bookingData.from
        );

        await this.homePage.clickSearch();

        await this.selectFlightAndSeat();

        await this.enterPassengerDetails();

        await this.makePayment();

        await this.verifyBookingSuccess();

    }

}