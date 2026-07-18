import { Page } from "@playwright/test";
import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { BusListingPage } from "../pages/BusListingPage";
import { SeatSelectionPage } from "../pages/SeatSelectionPage";
import { PassengerInformationPage } from "../pages/passengerInformationPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { TicketPage } from "../pages/TicketPage";
import { AppLogger } from "../utils/logger";

export class BookingFlow {

    private readonly loginPage: LoginPage;
    private readonly homePage: HomePage;
    private readonly busListingPage: BusListingPage;
    private readonly seatSelectionPage: SeatSelectionPage;
    private readonly passengerInformationPage: PassengerInformationPage;
    private readonly checkoutPage: CheckoutPage;
    private readonly ticketPage: TicketPage;

    constructor(private readonly page: Page,private readonly log: AppLogger) {

        this.loginPage = new LoginPage(page);
        this.homePage = new HomePage(page);
        this.busListingPage = new BusListingPage(page);
        this.seatSelectionPage = new SeatSelectionPage(page);
        this.passengerInformationPage = new PassengerInformationPage(page);
        this.checkoutPage = new CheckoutPage(page);
        this.ticketPage = new TicketPage(page);

    }

    async login(username: string, password: string): Promise<void> {

        this.log.info("Opening login page");
        await this.loginPage.open();

        this.log.info("Entering user credentials", {email: username});

        await this.loginPage.enterEmail(username);
        await this.loginPage.enterPassword(password);

        this.log.info("Signing in");
        await this.loginPage.clickSignIn();
        this.log.info("Login successful");
    }

    async searchBus(fromCity: string, toCity: string, date: string): Promise<void> {

        this.log.info("Searching buses", {  from: fromCity,  to: toCity,  date  });

        await this.homePage.verifyHomePage();
        await this.homePage.switchBuses();
        await this.homePage.enterFrom(fromCity);
        await this.homePage.enterDestination(toCity);
        await this.homePage.selectDate(date);
        await this.homePage.clickSearch();
        this.log.info("Bus search completed");

    }

    async filterSearchResults(): Promise<void> {

        this.log.info("Applying Semi-Sleeper filter");

        await this.busListingPage.verifyListingPage();
        await this.busListingPage.selectBusType();
        await this.busListingPage.verifyTypeResults();
        await this.busListingPage.clickSelect();

        this.log.info("Bus selected successfully");

    }

    async selectSeats(): Promise<void> {

        this.log.info("Selecting seat");

        await this.seatSelectionPage.verifySelectionPage();
        await this.seatSelectionPage.selectSeat();

        this.log.info("Selecting boarding point");

        await this.seatSelectionPage.selectBoarding();

        this.log.info("Selecting dropping point");

        await this.seatSelectionPage.selectDroping();

        await this.seatSelectionPage.clickContinue();

        this.log.info("Seat selection completed");

    }

    async passengerInformation( firstName: string, lastName: string, age: string, gender: string, email: string, phoneNumber: string): Promise<void> {

        this.log.info("Entering passenger information", {
            firstName,
            lastName,
            age,
            gender,
            email,
            phoneNumber
        });
        await this.passengerInformationPage.fillFirstName(firstName);
        await this.passengerInformationPage.fillLastName(lastName);
        await this.passengerInformationPage.fillAge(age);
        await this.passengerInformationPage.fillGender(gender);
        await this.passengerInformationPage.fillEmail(email);
        await this.passengerInformationPage.fillPhoneNumber(phoneNumber);
        await this.passengerInformationPage.clickContinue();

        this.log.info("Passenger information submitted");

    }

    async checkout( name: string,number: string,expiry: string,cvv: string ): Promise<void> {

        this.log.info("Entering payment details", {
            cardHolder: name,
            cardNumber: number,
            expiry,
            cvv
        });
        await this.checkoutPage.fillName(name);
        await this.checkoutPage.fillNumber(number);
        await this.checkoutPage.fillExpiry(expiry);
        await this.checkoutPage.fillCVV(cvv);

        this.log.info("Submitting payment");

        await this.checkoutPage.clickPay();

        this.log.info("Payment submitted");

    }

    async verifyBookingOutcome(): Promise<"Confirmed" | "Payment Declined"> {

    this.log.info("Checking booking outcome");

    const outcome = await this.checkoutPage.getBookingOutcome();

    if (outcome === "Payment Declined") {
        this.log.info("Payment declined by gateway");
        return "Payment Declined";
    }

    this.log.info("Payment successful");

    await this.ticketPage.verifyTicket();

    this.log.info("Booking confirmed");

    return "Confirmed";
}

}