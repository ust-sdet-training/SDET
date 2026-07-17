import { LoginPage } from "../pages/LoginPage";
import { SearchPage } from "../pages/SearchPage";
import { ResultsPage } from "../pages/ResultsPage";
import { SeatPage } from "../pages/SeatPage";
import { PassengerPage } from "../pages/PassengerPage";
import { PaymentPage } from "../pages/PaymentPage";
import { ConfirmationPage } from "../pages/ConfirmationPage";
import { FlightData } from "../data/FlightData";
import { PassengerData } from "../data/PassengerData";
import { Page } from "@playwright/test";
import { logger } from "../logger/Logger";

export class TripFlow {
  constructor(private page: Page) {}

  async completeFlightJourney(username: string, password: string) {
    logger.info("Starting completeFlightJourney");
    const loginPage = new LoginPage(this.page);
    const searchPage = new SearchPage(this.page);
    const resultsPage = new ResultsPage(this.page);
    const seatPage = new SeatPage(this.page);
    const passengerPage = new PassengerPage(this.page);
    const paymentPage = new PaymentPage(this.page);
    const confirmationPage = new ConfirmationPage(this.page);
    const flightData = FlightData.roundTrip;

    logger.info(`Logging in as ${username}`);
    await loginPage.open();
    await loginPage.login(username, password);

    logger.info("Searching flights");
    await searchPage.openFlightsSearch();
    await searchPage.searchRoundTrip(flightData);

    logger.info("Selecting first flight from results");
    await resultsPage.verifyResultsPage();
    await resultsPage.selectFirstFlight();

    const seat = await seatPage.selectFirstAvailableSeat();
    logger.info(`Selected seat ${seat}`);
    await seatPage.continueToPassenger();

    logger.info("Filling passenger details");
    await passengerPage.fillPassengerDetails(
      PassengerData.firstName,
      PassengerData.lastName,
      PassengerData.age,
      PassengerData.gender,
      PassengerData.email,
      PassengerData.phone,
    );
    await passengerPage.continueToPayment();
    const cardHolderName =
      `${PassengerData.firstName} ${PassengerData.lastName}`.trim();
    logger.info("Filling payment details");
    await paymentPage.fillPaymentDetails(
      cardHolderName,
      "4111111111111111",
      "12/30",
      "123",
    );
    await paymentPage.payNow();

    logger.info("Waiting for confirmation");
    await confirmationPage.verifyConfirmation();
    logger.info("Completed journey");
  }
}
