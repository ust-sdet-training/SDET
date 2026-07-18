import { Page } from "@playwright/test";

export class PassengerPage {

    constructor(private page: Page) {}

    async enterPassenger() {

        await this.page.getByRole("textbox", { name: "First name" }).fill("Erin");

        await this.page.getByRole("textbox", { name: "Last name" }).fill("Tripstack");

        await this.page.getByRole("spinbutton", { name: "Age" }).fill("22");

        await this.page.getByRole("textbox", { name: "Email" }).fill("erin@tripstack.test");

        await this.page.getByRole("textbox", { name: "Phone number" }).fill("1234567890");

        await this.page.getByRole("button", {name: "Continue to payment"}).click();
    }

}