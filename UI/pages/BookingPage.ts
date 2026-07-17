import { test, Page, expect } from '@playwright/test';

export class BookingPage {
    constructor(private readonly page: Page) {}

    async busSearch(from: string, to: string, date: string) {
        await this.page.goto('https://tripstack.doomple.com/');
        await this.page.getByRole('tab', { name: 'Buses' }).click();
        await this.page.getByRole('combobox', { name: 'From' }).click();
        await this.page.getByRole('option', { name: from }).click();
        await this.page.getByRole('combobox', { name: 'To' }).click();
        await this.page.getByRole('option', { name: to }).click();
        await this.page.getByRole('textbox', { name: 'Date' }).fill(date);
        await this.page.getByRole('button', { name: 'Search' }).click();
    }

    async selectFirstAC() {
        await this.page.getByRole('checkbox', { name: 'A/C Sleeper' }).check();
        await this.page.getByRole('button', { name: 'Select Seats' }).first().click();
    }

    async selectSeats(seat: string) {
        await this.page.getByRole('button', { name: seat }).click();
        await this.page.getByRole('button', { name: 'Continue to passenger details' }).click();
    }

    async passengerDetails(firstName: string, lastName: string, age: string, email: string, phone: string) {
        await this.page.getByRole('textbox', { name: 'First name' }).click();
        await this.page.getByRole('textbox', { name: 'First name' }).fill(firstName);
        await this.page.getByRole('textbox', { name: 'Last name' }).click();
        await this.page.getByRole('textbox', { name: 'Last name' }).fill(lastName);
        await this.page.getByRole('spinbutton', { name: 'Age' }).click();
        await this.page.getByRole('spinbutton', { name: 'Age' }).fill(age);
        await this.page.getByRole('textbox', { name: 'Email' }).click();
        await this.page.getByRole('textbox', { name: 'Email' }).fill(email);
        await this.page.getByRole('textbox', { name: 'Phone number' }).click();
        await this.page.getByRole('textbox', { name: 'Phone number' }).fill(phone);
        await this.page.getByRole('button', { name: 'Continue to payment' }).click();
    }

    async paymentDetails(cardName: string, cardNumber: string, expiry: string, cvv: string) {
        await this.page.getByRole('textbox', { name: 'Name on card' }).click();
        await this.page.getByRole('textbox', { name: 'Name on card' }).fill(cardName);
        await this.page.getByRole('textbox', { name: 'Card number' }).click();
        await this.page.getByRole('textbox', { name: 'Card number' }).fill(cardNumber);
        await this.page.getByRole('textbox', { name: 'Expiry' }).click();
        await this.page.getByRole('textbox', { name: 'Expiry' }).fill(expiry);
        await this.page.getByRole('textbox', { name: 'CVV' }).click();
        await this.page.getByRole('textbox', { name: 'CVV' }).fill(cvv);
        await this.page.getByRole('button', { name: 'Pay ₹' }).click();
        await expect(this.page.getByText('CONFIRMED', { exact: true })).toBeVisible();
    }

    async getPnr(): Promise<string> {
        return await this.page.locator('[data-id="pnr"]').innerText();
    }

    async viewBooking(pnr: string) {
        await this.page.getByRole('button', { name: 'View my trips' }).click();
        await expect(this.page.getByText(pnr)).toBeVisible();
    }

    async cancel() {
        await this.page.getByRole('button', { name: 'Cancel' }).click();
    }
}