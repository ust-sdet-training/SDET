import { test as base } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { FlightSearchPage } from "../pages/FlightSearchPage";
import { BookingPage } from "../pages/BookingPage";
import { CabinPage } from "../pages/CabinPage";
import { DetailsPage } from "../pages/DetailsPage";
import { CardPage } from "../pages/CardPage";
import { ConfirmedPage } from "../pages/ConfirmedPage";

export const test = base.extend<{
    pages: {
        login: LoginPage;
        flight: FlightSearchPage;
        booking: BookingPage;
        cabin: CabinPage;
        details: DetailsPage;
        payment: CardPage;
        confirmation: ConfirmedPage;
    };
}>({

    pages: async ({ page }, use) => {

        await use({

            login: new LoginPage(page),

            flight: new FlightSearchPage(page),

            booking: new BookingPage(page),

            cabin: new CabinPage(page),

            details: new DetailsPage(page),

            payment: new CardPage(page),

            confirmation: new ConfirmedPage(page)

        });

    }

});

export { expect } from "@playwright/test";