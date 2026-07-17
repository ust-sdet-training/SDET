import { test as base, expect } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { SearchResultPage } from "../pages/SearchResultPage";
import { SeatSelectionPage } from "../pages/SeatSelectionPage";
import { PassengerPage } from "../pages/PassengerPage";
import { PaymentPage } from "../pages/PaymentPage";
import { BookingConfirmationPage } from "../pages/BookingConfirmationPage";

type Pages = {

    loginPage: LoginPage;
    homePage: HomePage;
    resultPage: SearchResultPage;
    seatPage: SeatSelectionPage;
    passengerPage: PassengerPage;
    paymentPage: PaymentPage;
    bookingConfirmationPage: BookingConfirmationPage;
};

export const test = base.extend<Pages>({

    loginPage: async ({ page }, use) => {
        await use(new LoginPage(page));
    },

    homePage: async ({ page }, use) => {
        await use(new HomePage(page));
    },

    resultPage: async ({ page }, use) => {
        await use(new SearchResultPage(page));
    },

    seatPage: async ({ page }, use) => {
        await use(new SeatSelectionPage(page));
    },

    passengerPage: async ({ page }, use) => {
        await use(new PassengerPage(page)); 
    },

    paymentPage: async ({ page }, use) => {
        await use(new PaymentPage(page));
    },

    bookingConfirmationPage: async ({ page }, use) => {     
        await use(new BookingConfirmationPage(page));
    }

});

export { expect };