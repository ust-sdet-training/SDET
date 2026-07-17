import { test as base } from '@playwright/test';

import { LoginPage } from '../pages/LoginPage';
import { HomePage } from '../pages/HomePage';
import { SearchResultPage } from '../pages/SearchResultPage';
import { PassengerPage } from '../pages/PassengerPage';
import { PaymentPage } from '../pages/PaymentPage';
import { BookingConfirmationPage } from '../pages/BookingConfirmationPage';
import { MyTripsPage } from '../pages/MyTripsPage';

import { LoginFlow } from '../flows/LoginFlow';
import { BookingFlow } from '../flows/BookingFlow';

type Fixtures = {

    loginPage: LoginPage;
    homePage: HomePage;
    searchResultPage: SearchResultPage;
    passengerPage: PassengerPage;
    paymentPage: PaymentPage;
    bookingConfirmationPage: BookingConfirmationPage;
    myTripsPage: MyTripsPage;

    loginFlow: LoginFlow;
    bookingFlow: BookingFlow;

};

export const test = base.extend<Fixtures>({

    loginPage: async ({ page }, use) => {
        await use(new LoginPage(page));
    },

    homePage: async ({ page }, use) => {
        await use(new HomePage(page));
    },

    searchResultPage: async ({ page }, use) => {
        await use(new SearchResultPage(page));
    },

    passengerPage: async ({ page }, use) => {
        await use(new PassengerPage(page));
    },

    paymentPage: async ({ page }, use) => {
        await use(new PaymentPage(page));
    },

    bookingConfirmationPage: async ({ page }, use) => {
        await use(new BookingConfirmationPage(page));
    },

    myTripsPage: async ({ page }, use) => {
        await use(new MyTripsPage(page));
    },

    loginFlow: async ({ loginPage }, use) => {

        await use(

            new LoginFlow(
                loginPage
            )

        );

    },

    bookingFlow: async (
        {
            homePage,
            searchResultPage,
            passengerPage,
            paymentPage,
            bookingConfirmationPage,
            myTripsPage
        },
        use
    ) => {

        await use(

            new BookingFlow(
                homePage,
                searchResultPage,
                passengerPage,
                paymentPage,
                bookingConfirmationPage,
                myTripsPage
            )

        );

    }

});

export { expect } from '@playwright/test';