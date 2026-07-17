import { test as base } from "@playwright/test";
import {LoginPage} from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { SearchResultPage } from "../pages/SearchResultPage";
import { PlanePage } from "../pages/PlanePage";
import { PaymentPage } from "../pages/PaymentPage";
import { BookingFlow } from "../flows/BookingFlow";
import { AppLogger, logger } from "../utils/logger";
import { InvalidSearchFlow } from "../flows/InvalidSearchFlow";
import { MyTripsPage } from "../pages/MyTripsPage";

type MyFixtures = {
    loginPage: LoginPage;
    homePage: HomePage;
    searchResultPage: SearchResultPage;
    planePage: PlanePage;
    bookFlow: BookingFlow;
    paymentPage: PaymentPage;
    negativeFlow: InvalidSearchFlow;
    tripsPage: MyTripsPage;
    log: AppLogger;
};

export const test = base.extend<MyFixtures>({
    log: async ({}: any, use: (arg0: any) => any) => {
        await use(logger);
    },

    loginPage: async ({ page }, use) => {

        await use(new LoginPage(page));

    },
    homePage: async ({ page }, use) => {

        await use(new HomePage(page));

    },

    searchResultPage: async ({ page }, use) => {

        await use(new SearchResultPage(page));

    },

    planePage: async ({ page }, use) => {

        await use(new PlanePage(page));

    },

    paymentPage: async ({ page }, use) => {

        await use(new PaymentPage(page));

    },

    tripsPage: async ({ page }, use) => {

        await use(new MyTripsPage(page));

    },

    bookFlow: async (

        {loginPage,homePage,searchResultPage,planePage,paymentPage,tripsPage, log},use) => {

        await use(new BookingFlow(loginPage,homePage,searchResultPage,planePage,paymentPage,tripsPage, log));

    },

    negativeFlow: async (

        {loginPage,homePage,searchResultPage, log},use) => {

        await use(new InvalidSearchFlow(loginPage,homePage,searchResultPage, log));

    }

});

export { expect } from "@playwright/test";