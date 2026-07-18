import { Page } from '@playwright/test';

import { Environment } from '../config/environment';
import { DateUtil } from '../utils/DateUtils';

import { LoginPage } from '../pages/LoginPage';
import { BusSearchPage } from '../pages/BusSearchPage';
import { BusResultsPage } from '../pages/BusResultsPage';
import { SeatSelectionPage } from '../pages/SeatSelectionPage';
import { PassengerPage } from '../pages/PassengerPage';
import { PaymentPage } from '../pages/PaymentPage';
import { ConfirmationPage } from '../pages/ConfirmationPage';
import { MyTripsPage } from '../pages/MyTripsPage';

export class BookingFlow {

    private loginPage: LoginPage;
    private busSearchPage: BusSearchPage;
    private busResultsPage: BusResultsPage;
    private seatSelectionPage: SeatSelectionPage;
    private passengerPage: PassengerPage;
    private paymentPage: PaymentPage;
    private confirmationPage: ConfirmationPage;
    private myTripsPage: MyTripsPage;

    constructor(page: Page) {

        this.loginPage = new LoginPage(page);
        this.busSearchPage = new BusSearchPage(page);
        this.busResultsPage = new BusResultsPage(page);
        this.seatSelectionPage = new SeatSelectionPage(page);
        this.passengerPage = new PassengerPage(page);
        this.paymentPage = new PaymentPage(page);
        this.confirmationPage = new ConfirmationPage(page);
        this.myTripsPage = new MyTripsPage(page);

    }

    async completeBookingJourney() {

        await this.loginPage.navigate();

        await this.loginPage.login(
            Environment.username,
            Environment.password
        );

        await this.loginPage.verifyLogin();

        await this.busSearchPage.openBusPage();

        await this.busSearchPage.selectFrom("Hyderabad HYD");

        await this.busSearchPage.selectTo("Delhi DEL");

        await this.busSearchPage.selectJourneyDate(
            DateUtil.getJourneyDate(19)
        );

        await this.busSearchPage.searchBus();

        await this.busResultsPage.filterACSeater();

        await this.busResultsPage.verifyBusDisplayed();

        await this.busResultsPage.selectBus();

        await this.seatSelectionPage.chooseSeat("S2");

        await this.seatSelectionPage.continue();

        await this.passengerPage.verifyPassengerPage();

        await this.passengerPage.enterPassengerDetails(
            Environment.firstName,
            Environment.lastName,
            Environment.age,
            Environment.gender,
            Environment.username,
            Environment.phone
        );

        await this.passengerPage.continueToPayment();

        await this.paymentPage.verifyPaymentPage();

        await this.paymentPage.enterCardDetails(
            Environment.cardName,
            Environment.cardNumber,
            Environment.expiry,
            Environment.cvv
        );

        try {

            await this.paymentPage.payNow();

            await this.paymentPage.page.waitForTimeout(2000);

            if (await this.paymentPage.isPaymentDeclined()) {

                console.log("Payment declined by gateway.");

                console.log("Fault Injection detected and handled successfully.");
                return;

            }


            await this.confirmationPage.verifyBookingSuccessful();

            await this.confirmationPage.verifyPNR();

            await this.confirmationPage.verifyAmountPaid();

            await this.confirmationPage.openMyTrips();

            await this.myTripsPage.verifyTripExists();

        }
        catch (error) {

            console.error("Unexpected error during booking flow.");

            throw error;

        }

    }

}