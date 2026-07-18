import { Page, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { BookingPage } from '../pages/BookingPage';

export class TripFlow {
    private readonly loginPage: LoginPage;
    private readonly bookingPage: BookingPage;

    constructor(private readonly page: Page) {
        this.loginPage = new LoginPage(page);
        this.bookingPage = new BookingPage(page);
    }

    async validLogin(email: string, password: string) {
        await this.loginPage.goto();
        await this.loginPage.login(email, password);
    }

    async searchForBuses(from: string, to: string, date: string) {
        await this.bookingPage.busSearch(from, to, date);
    }

    async selectSeatsForFirstBus(seat: string, deck:string) {
        await this.bookingPage.selectFirstAC();
        await this.bookingPage.selectSeats(seat, deck);
    }

    async fillPassengerDetails(firstName: string, lastName: string, age: string, email: string, phone: string) {
        await this.bookingPage.passengerDetails(firstName, lastName, age, email, phone);
    }

    async fillPaymentDetails(cardName: string, cardNumber: string, expiry: string, cvv: string): Promise<boolean> {
        return await this.bookingPage.paymentDetails(cardName, cardNumber, expiry, cvv);
    }

    async verifyBookingIsConfirmed() {
        const pnr = await this.bookingPage.getPnr();
        await this.bookingPage.viewBooking(pnr);
    }

    async cancelTrip() {
        await this.bookingPage.cancel();
    }
}