import { Page } from "@playwright/test";
import 'dotenv/config';
import { LoginPage } from "../pages/LoginPage";
import { FlightSearchPage } from "../pages/FlightSearchPage";
import { BookingPage } from "../pages/BookingPage";
import { CabinPage } from "../pages/CabinPage";
import { DetailsPage } from "../pages/DetailsPage";
import { CardPage } from "../pages/CardPage";
import { ConfirmedPage } from "../pages/ConfirmedPage";

import { config } from "../data/config";
import { journey } from "../data/journeyData";
import { passenger } from "../data/passengerData";
import { card } from "../data/cardData";
import { DateUtils } from "../utils/DateUtil";

export class BookingFlow {

    private loginPage: LoginPage;
    private flightSearchPage: FlightSearchPage;
    private bookingPage: BookingPage;
    private cabinPage: CabinPage;
    private detailsPage: DetailsPage;
    private paymentPage: CardPage;
    private confirmedPage: ConfirmedPage;

    constructor(page: Page) {

        this.loginPage = new LoginPage(page);
        this.flightSearchPage = new FlightSearchPage(page);
        this.bookingPage = new BookingPage(page);
        this.cabinPage = new CabinPage(page);
        this.detailsPage = new DetailsPage(page);
        this.paymentPage = new CardPage(page);
        this.confirmedPage = new ConfirmedPage(page);

    }

    async completeBooking() {

        await this.loginPage.navigate(config.baseUrl);

        await this.loginPage.openLoginPage();

        await this.loginPage.login(
            config.email,
            config.password
        );

        await this.flightSearchPage.searchFlight(
            journey.source,
            journey.destination,
            DateUtils.getFutureDate(journey.days)
        );

        await this.bookingPage.bookFirstFlight();

        await this.cabinPage.selectSeatAndContinue();

        await this.detailsPage.enterPassengerDetails(
            passenger.firstName,
            passenger.lastName,
            passenger.age,
            "Female",
            passenger.email,
            passenger.phone
        );

        await this.paymentPage.makePayment(
            card.cardHolderName,
            card.cardNumber,
            card.expiry,
            card.cvv
        );

    }

    async verifyBooking() {

        await this.confirmedPage.verifyBookingConfirmed();

    }

    async getBookingReference() {

        return await this.confirmedPage.getBookingReference();

    }

}