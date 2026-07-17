import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { BusSearchResultsPage } from "../pages/BusSearchResultsPage";
import { SeatSelectionPage } from "../pages/SeatSelectionPage";
import { PassengerDetailsPage } from "../pages/PassengerDetailsPage";
import { PaymentPage } from "../pages/PaymentPage";
import { BookingConfirmationPage } from "../pages/BookingConfirmationPage";
import { MyTripsPage } from "../pages/MyTripsPage";
import { logger } from "../logger/Logger";

export class BusBookingFlow {

    constructor(
        private loginPage: LoginPage,
        private homePage: HomePage,
        private busSearchResultsPage: BusSearchResultsPage,
        private seatSelectionPage: SeatSelectionPage,
        private passengerDetailsPage: PassengerDetailsPage,
        private paymentPage: PaymentPage,
        private bookingConfirmationPage: BookingConfirmationPage,
        private myTripsPage: MyTripsPage
    ) {}

    async login(username: string, password: string) {
        logger.info("Starting login flow");
        await this.loginPage.open();
        await this.loginPage.verifyLoginPage();
        await this.loginPage.login(username, password);
        logger.info("Login submitted");
    }

    async searchBus(from: string, to: string, date: string) {
        logger.info(`Searching bus from ${from} to ${to} on ${date}`);
        await this.homePage.verifyHomePage();
        await this.homePage.searchBus(from, to, date);
    }

    async selectBus() {
        logger.info("Selecting first available bus");
        await this.busSearchResultsPage.verifyResultsLoaded();
        await this.busSearchResultsPage.selectFirstBus();
    }

    async selectSeat(seatNo: string) {
        logger.info(`Choosing seat ${seatNo}`);
        await this.seatSelectionPage.verifySeatMapLoaded();
        await this.seatSelectionPage.selectSeatAndContinue(seatNo);
    }

    async enterPassengerDetails(
        firstName: string,
        lastName: string,
        age: string,
        gender: string,
        email: string,
        phone: string
    ) {
        logger.info(`Entering passenger details for ${firstName} ${lastName}`);
        await this.passengerDetailsPage.enterPassengerDetails(
            firstName,
            lastName,
            age,
            gender,
            email,
            phone
        );
    }

    async makePayment(
        cardName: string,
        cardNumber: string,
        expiry: string,
        cvv: string
    ) {
        logger.info("Starting payment flow");
        await this.paymentPage.verifyPaymentPage();

        await this.paymentPage.makePayment(
            cardName,
            cardNumber,
            expiry,
            cvv
        );
        logger.info("Payment submitted");
    }

    async verifyBooking() {
        logger.info("Verifying booking confirmation");
        await this.bookingConfirmationPage.verifyBookingConfirmationPage();
        await this.bookingConfirmationPage.verifyPNRFormat();
        logger.info("Booking verified");
    }

    async openMyTrips() {
        logger.info("Opening My Trips page");
        await this.bookingConfirmationPage.clickViewMyTrips();
        await this.myTripsPage.verifyMyTripsPage();
        logger.info("My Trips page opened");
    }
}