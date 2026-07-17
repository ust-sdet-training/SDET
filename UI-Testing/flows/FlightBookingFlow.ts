import { Page } from "@playwright/test";

import { HomePage } from "../pages/HomePage";
import { LoginPage } from "../pages/LoginPage";
import { FlightsPage } from "../pages/FlightsPage";

import {PassengerData, CardData, TestData} from "../constants/TestData";
import { BasePage } from "../pages/BasePage";
import { AppLogger } from "../utils/Logger";
import { SeatingPage } from "../pages/SeatingPage";
import { DetailsPage } from "../pages/DetailsPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { ConfirmationPage } from "../pages/ConfirmationPage";

export class FlightBookingFlow extends BasePage {

    constructor(page: Page, log: AppLogger) {
        super(page, log)
    }

    async bookFlight(): Promise<void> {

        const homePage = new HomePage(this.page, this.log);
        const loginPage = new LoginPage(this.page, this.log)
        const flightsPage = new FlightsPage(this.page, this.log);
        const seatingPage = new SeatingPage(this.page, this.log);
        const detailsPage = new DetailsPage(this.page, this.log);
        const checkoutPage = new CheckoutPage(this.page, this.log);
        const confirmationPage = new ConfirmationPage(this.page, this.log);

        
        await homePage.gotoLoginPage();
        // Login
        await loginPage.signIn(TestData.email, TestData.password)

        // Search Flight
        await homePage.searchFlight(TestData.from, TestData.to, TestData.date)

        // Booking flight
        await flightsPage.verifyFlightResultsDisplayed();
        await flightsPage.clickBookBtn()

        // Seat Selection
        await seatingPage.verifySeatingPage();
        const selectedSeat = await seatingPage.selectAvailableSeat();
        await seatingPage.verifySeatSelected(selectedSeat);
        await seatingPage.clickContinue();

        // Passenger Details
        await detailsPage.verifyDetailsPage()
        await detailsPage.fillPassengerDetails(PassengerData)

        await detailsPage.continueToPayment();

        // Payment
        await checkoutPage.verifyCheckoutPage();
        await checkoutPage.fillCardDetails(CardData)
        await checkoutPage.clickPay()

        // Confirmation
        await confirmationPage.verifyBookingConfirmed();
    }

}