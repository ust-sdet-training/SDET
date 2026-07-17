import { test as base, expect } from "@playwright/test";
import { LoginPage } from "../pages/LoginPage";
import { SearchPage } from "../pages/SearchPage";
import { ResultsPage } from "../pages/ResultsPage";
import { SeatPage } from "../pages/SeatPage";
import { PassengerPage } from "../pages/PassengerPage";
import { PaymentPage } from "../pages/PaymentPage";
import { ConfirmationPage } from "../pages/ConfirmationPage";

type MyFixtures = {
  loginPage: LoginPage;
  searchPage: SearchPage;
  resultsPage: ResultsPage;
  seatPage: SeatPage;
  passengerPage: PassengerPage;
  paymentPage: PaymentPage;
  confirmationPage: ConfirmationPage;
};

export const test = base.extend<MyFixtures>({
  loginPage: async ({ page }, use) => {
    await use(new LoginPage(page));
  },

  searchPage: async ({ page }, use) => {
    await use(new SearchPage(page));
  },

  resultsPage: async ({ page }, use) => {
    await use(new ResultsPage(page));
  },

  seatPage: async ({ page }, use) => {
    await use(new SeatPage(page));
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
});

export { expect };
