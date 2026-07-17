import { Page } from "@playwright/test";

import { Header } from "../pageobjects/components/Header";
import { HomePage } from "../pageobjects/HomePage";
import { LoginPage } from "../pageobjects/LoginPage";
import { FlightSearchPage } from "../pageobjects/FlightSearchPage";
import { FlightSearchResultsPage } from "../pageobjects/FlightSearchResultsPage";
import { FlightSeatMapPage } from "../pageobjects/FlightSeatMapPage";
import { MyTripsPage } from "../pageobjects/MyTripsPage";
import { PassengerDetailsPage } from "../pageobjects/PassengerDetailsPage";
import { PaymentDetailsPage } from "../pageobjects/PaymentDetailsPage";
import { TicketConfirmationPage } from "../pageobjects/TicketConfirmationPage";


export class FlightFlow {

    readonly header;
    readonly home;
    readonly login;
    readonly search;
    readonly results;
    readonly seats;
    readonly trips;
    readonly passenger;
    readonly payment;
    readonly confirmation;

    constructor(page: Page) {
        this.header = new Header(page);
        this.home = new HomePage(page);
        this.login = new LoginPage(page);
        this.search = new FlightSearchPage(page);
        this.results = new FlightSearchResultsPage(page);
        this.seats = new FlightSeatMapPage(page);
        this.trips = new MyTripsPage(page);
        this.passenger = new PassengerDetailsPage(page);
        this.payment = new PaymentDetailsPage(page);
        this.confirmation = new TicketConfirmationPage(page);
    }

    async openWebsite() {
        await this.home.open();
        await this.home.verifyHomePageLoaded();
    }

    async doLogin(email: string,password: string) {
        await this.header.clickLogInLink();
        await this.login.login(email,password);
        await this.header.verifyLogIn();
    }

    async searchFlight(from: string,fromOption: string, to: string, toOption: string, date: string) {
        await this.header.clickFlightsLink();
        await this.search.verifyFlightSearchPageLoaded();
        await this.search.searchFlight(from,fromOption, to,toOption, date);
    }

    async verifyNoFlightsForInvalidRouteSearch(){
        await this.results.invalidRouteSearchResult();
    }

    async sortAndSelectFlight(option: string, flightName: string) {
        await this.results.verifyFlightSearchResultsPageLoaded();
        await this.results.sortBy(option);
        await this.results.bookFlight(flightName);
    }

    async selectSeat(seat: string) {
        await this.seats.verifyFlightSeatMapPageLoaded();
        await this.seats.bookSeat(seat);
    }

    async enterPassengerDetails(firstName: string, lastName: string, age: string, gender: string, email: string, phoneNumber: string) {
        await this.passenger.verifyPassengerDetailsPageLoaded();
        await this.passenger.fillPassengerDetails(firstName, lastName, age, gender, email, phoneNumber)
    }

    async enterPaymentDetails(nameOnCard: string, cardNumber: string, expiryDate: string, cvv: string) {
        await this.payment.verifyPaymentDetailsPageLoaded();
        await this.payment.fillPaymentDetails(nameOnCard, cardNumber, expiryDate, cvv);
    }

    async verifyTicketConfirmation(status:string) {
        await this.confirmation.verifyTicketConfirmationPageLoaded();
        await this.confirmation.verifyConfirmedStatus();
        await this.confirmation.clickViewMyTrips();
        await this.trips.verifyMyTripsPageLoaded();
        await this.trips.verifyBookingStatus(status);
    }

    async cleanBookings(status: string){
        await this.trips.cancelBooking();
        await this.trips.verifyBookingStatus(status);
    }
}

