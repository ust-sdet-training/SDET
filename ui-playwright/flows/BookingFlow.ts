import { Page } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { SearchResultsPage } from "../pages/SearchResultsPage";
import { SeatSelectionPage } from "../pages/SeatSelectionPage";
import { PassengerPage } from "../pages/PassengerPage";
import { PaymentPage } from "../pages/PaymentPage";
import { ConfirmationPage } from "../pages/ConfirmationPage";

export class BookingFlow {

    login: LoginPage;
    home: HomePage;
    results: SearchResultsPage;
    seat: SeatSelectionPage;
    passenger: PassengerPage;
    payment: PaymentPage;
    confirmation: ConfirmationPage;

    constructor(page: Page) {
        this.login = new LoginPage(page);
        this.home = new HomePage(page);
        this.results = new SearchResultsPage(page);
        this.seat = new SeatSelectionPage(page);
        this.passenger = new PassengerPage(page);
        this.payment = new PaymentPage(page);
        this.confirmation = new ConfirmationPage(page);
    }

    async completeBooking(
        login: any,
        trip: any,
        passenger: any,
        payment: any
    ) {

        await this.login.login(
            login.email,
            login.password
        );

        await this.home.searchFlight(
            trip.from,
            trip.fromAirport,
            trip.to,
            trip.toAirport,
            trip.date
        );

        await this.results.bookFlight(
            trip.route,
            trip.flight
        );

        await this.seat.chooseSeat(
            "Seat 3A, window, available"
        );

        await this.passenger.enterPassengerDetails(
            passenger.firstName,
            passenger.lastName,
            passenger.age,
            passenger.gender,
            login.email,
            passenger.phone
        );

        await this.payment.pay(
            payment.cardHolder,
            payment.cardNumber,
            payment.expiry,
            payment.cvv
        );

        await this.confirmation.verifyBooking();
    }
    
}