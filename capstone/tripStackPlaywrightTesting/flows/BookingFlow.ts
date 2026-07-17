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
    ) {}

    // Search Flight
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

    // Select Flight & Seat
    async selectFlightAndSeat() {

    await this.searchResultPage.verifySearchResultsLoaded();

    await this.searchResultPage.clickBook();

    while (true) {

        await this.searchResultPage.selectSeat();

        await this.searchResultPage.continueToPassengerDetails();

        const seatUnavailable = await this.searchResultPage.page
            .getByText('Seat no longer available')
            .isVisible()
            .catch(() => false);

        if (!seatUnavailable) {
            break;
        }

        console.log('Seat unavailable. Trying another seat...');

        await this.searchResultPage.page.getByRole('button', {
            name: 'Back'
        }).click();

    }

}

    // Passenger Details
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

    // Payment
    async makePayment() {

        await this.paymentPage.verifyPaymentPageLoaded();

        await this.paymentPage.makePayment();

    }

    // Booking Confirmation
    async verifyBookingSuccess() {

        await this.bookingConfirmationPage.verifyBookingSuccess();

        await this.bookingConfirmationPage.verifyJourneyDetails();

    }

    // Open My Trips
    async openMyTrips() {

        await this.bookingConfirmationPage.clickViewMyTrips();

        await this.myTripsPage.verifyMyTripsPageLoaded();

    }

    // Verify Booking
    async verifyBookingInTrips() {

        await this.myTripsPage.verifyBookingExists();

    }

    // Complete One Way Booking
    async completeBooking() {

        await this.searchFlight();

        await this.selectFlightAndSeat();

        await this.enterPassengerDetails();

        await this.makePayment();

        await this.verifyBookingSuccess();

        await this.openMyTrips();

        await this.verifyBookingInTrips();

    }

    // Complete Round Trip (Book Outbound + Return Separately)
    async completeRoundTripBooking() {

        // Outbound Journey
        await this.searchFlight();

        await this.selectFlightAndSeat();

        await this.enterPassengerDetails();

        // Return to Home
        await this.homePage.page.goto('/');

        await this.homePage.verifyHomePageLoaded();

        // Return Journey
        await this.homePage.enterSource(
            bookingData.to
        );

        await this.homePage.enterDestination(
            bookingData.from
        );

        await this.homePage.clickSearch();

        await this.selectFlightAndSeat();

        await this.enterPassengerDetails();

    }

}