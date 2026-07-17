import { test as base } from '@playwright/test';

// Pages
import { LoginPage } from '../pages/LoginPage';
import { HomePage } from '../pages/HomePage';
import { SearchResultsPage } from '../pages/SearchResultsPage';
import { SeatSelectionPage } from '../pages/SeatSelectionPage';
import { PassengerPage } from '../pages/PassengerPage';
import { PaymentPage } from '../pages/PaymentPage';
import { ConfirmationPage } from '../pages/ConfirmationPage';
import { MyTripsPage } from '../pages/MyTripsPage';

// Flows
import { LoginFlow } from '../flows/LoginFlow';
import { FlightBookingFlow } from '../flows/FlightBookingFlow';
import { MyTripsFlow } from '../flows/MyTripsFlow';

type Fixtures = {

    loginPage: LoginPage;
    homePage: HomePage;
    searchResultsPage: SearchResultsPage;
    seatSelectionPage: SeatSelectionPage;
    passengerPage: PassengerPage;
    paymentPage: PaymentPage;
    confirmationPage: ConfirmationPage;
    myTripsPage: MyTripsPage;

    loginFlow: LoginFlow;
    bookingFlow: FlightBookingFlow;
    myTripsFlow: MyTripsFlow;

};

export const test = base.extend<Fixtures>({

    loginPage: async ({ page }, use) => {
        await use(new LoginPage(page));
    },

    homePage: async ({ page }, use) => {
        await use(new HomePage(page));
    },

    searchResultsPage: async ({ page }, use) => {
        await use(new SearchResultsPage(page));
    },

    seatSelectionPage: async ({ page }, use) => {
        await use(new SeatSelectionPage(page));
    },

    passengerPage: async ({ page }, use) => {
        await use(new PassengerPage(page));
    },

    paymentPage: async ({ page }, use) => {
        await use(new PaymentPage(page));
    },

    confirmationPage: async ({ page }, use) => {
        await use(new ConfirmationPage(page));
    },

    myTripsPage: async ({ page }, use) => {
        await use(new MyTripsPage(page));
    },

    loginFlow: async ({ loginPage }, use) => {

        await use(new LoginFlow(loginPage));

    },

    bookingFlow: async (
        {
            homePage,
            searchResultsPage,
            seatSelectionPage,
            passengerPage,
            paymentPage,
            confirmationPage
        },
        use
    ) => {

        await use(

            new FlightBookingFlow(

                homePage,
                searchResultsPage,
                seatSelectionPage,
                passengerPage,
                paymentPage,
                confirmationPage

            )

        );

    },

    myTripsFlow: async ({ myTripsPage }, use) => {

        await use(new MyTripsFlow(myTripsPage));

    }

});

export { expect } from '@playwright/test';