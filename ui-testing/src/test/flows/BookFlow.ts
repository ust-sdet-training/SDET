import { expect, type Page } from "@playwright/test";
import { BookingPage } from "../../main/ui/pages/BookingPage";
import { BusDetailPage } from "../../main/ui/pages/BusDetailPage";
import { BusListPage } from "../../main/ui/pages/BusListPage";
import { ConfirmationPage } from "../../main/ui/pages/ConfirmationPage";
import { HomePage } from "../../main/ui/pages/HomePage";
import { LoginPage } from "../../main/ui/pages/LoginPage";
import { PaymentPage } from "../../main/ui/pages/PaymentPage";
import { testUsers } from "../fixtures/test-users";

export class BookFlow{
    readonly bookingPage: BookingPage;
    readonly busDetailPage: BusDetailPage;
    readonly busListPage: BusListPage;
    readonly confirmationPage: ConfirmationPage;
    readonly homePage: HomePage;
    readonly loginPage: LoginPage;
    readonly paymentPage: PaymentPage;
    constructor(private readonly page: Page) {
        this.bookingPage = new BookingPage(page);
        this.busDetailPage = new BusDetailPage(page);
        this.busListPage = new BusListPage(page);
        this.confirmationPage = new ConfirmationPage(page);
        this.homePage = new HomePage(page);
        this.loginPage = new LoginPage(page);
        this.paymentPage = new PaymentPage(page);
    }

    loginSuccessFlow = async () => {
        await this.loginPage.goto();
        await this.loginPage.login(testUsers.user.email, testUsers.user.password);

        // await expect(this.page).toHaveURL(/\/home/);
        // await expect.soft(this.page.getByRole('heading', {name: `Welcome, ${testUsers.customer.displayName}`})).toBeVisible();
        // await expect.soft(this.page.getByRole('button', { name: 'Sign out' }));
    }

    // loginFailureFlow = async () => {
    //     await this.loginPage.goto();
    //     await this.loginPage.login(testUsers.invalid.email, testUsers.invalid.password);

    //     await expect.soft(this.page).toHaveURL(/\/login/);
    //     await expect.soft(this.page.getByTestId('login-error')).toBeVisible();
    // }

    travellerDetails = async (email: string, phone: string) => {
        await this.bookingPage.travellerDetails(email, phone);
    };

    selectSeat = async () => {
        await this.busDetailPage.selectSeat();
    }

    selectBus = async (busName: string) => {
        await this.busListPage.selectBus(busName);
    }

    confirmSeat = async () => {
        await this.confirmationPage.confirmSeat();
    }

    search = async (from: string, to: string, date: string) => {
        await this.homePage.search(from, to, date)
    }

    payment = async () => {
        await this.paymentPage.payment();
    }
}