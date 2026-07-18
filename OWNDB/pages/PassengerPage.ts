import { Page } from "@playwright/test";

export class PassengerPage {

    constructor(private page: Page) {}

    async enterPassenger(firstName: string, lastName: string, age: string, email: string, phone: string) {

        await this.page.getByRole("textbox", { name: "First name" }).fill(firstName);

        await this.page.getByRole("textbox", { name: "Last name" }).fill(lastName);

        await this.page.getByRole("spinbutton", { name: "Age" }).fill(age);

        await this.page.getByRole("textbox", { name: "Email" }).fill(email);

        await this.page.getByRole("textbox", { name: "Phone number" }).fill(phone);

        await this.page.getByRole("button", {name: "Continue to payment"}).click();
    }

}