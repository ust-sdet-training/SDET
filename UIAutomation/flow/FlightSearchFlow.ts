import { Page } from "@playwright/test";
import { HomePage } from "../pages/HomePage";
import { FlightFilterPage } from "../pages/FlightFilterPage";
import { FlightResultsPage } from "../pages/FlightResultsPage";
import { SeatSelectionPage } from "../pages/SeatSelectionPage";
import { LoginPage } from "../pages/LoginPage";
import { AppLogger } from "../src/logger";

export class FlightSearchFlow {
  private homePage: HomePage;
  private filterPage: FlightFilterPage;
  private resultsPage: FlightResultsPage;
  private seatPage: SeatSelectionPage;
  private loginPage: LoginPage;

  constructor(private page: Page, private log: AppLogger) {
    this.homePage = new HomePage(page, log);
    this.filterPage = new FlightFilterPage(page, log);
    this.resultsPage = new FlightResultsPage(page, log);
    this.seatPage = new SeatSelectionPage(page, log);
    this.loginPage = new LoginPage(page, log);
  }

   async loginUser(email: string, password: string) {
    await this.loginPage.login(email, password);
  }
  async searchAndBook(baseUrl: string) {
    this.log.info("========== Flight Search Flow Started ==========");

    await this.homePage.open(baseUrl);
    await this.homePage.verifyHomePage();
    await this.homePage.selectFrom("Bengauluru", "BLR");
    await this.homePage.selectTo("Kolkata", "CCU");
    await this.homePage.selectDate("2026-07-29");
    await this.homePage.searchFlights();

    await this.filterPage.filterByAirlines(["IndiGo", "Vistara", "SpiceJet", "Air India"]);
    await this.filterPage.filterByTimeSlots([
      "Before 6 AM",
      "AM – 12 PM",
      "PM – 6 PM",
      "After 6 PM",
    ]);
    await this.filterPage.setMaxPrice("641306");

    await this.resultsPage.bookFlight("IndiGo 6E-494");

    await this.seatPage.selectSeat("Seat 3A, window, available");

    this.log.info("========== Flight Search Flow Completed ==========");
  }

 
}