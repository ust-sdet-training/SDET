import { Page } from "@playwright/test";

export class PaymentPage {

    constructor(private page: Page) {}

    async pay() {

        await this.page.getByRole("textbox", {name: "Name on card"}).fill("Erin Tripstack");

        await this.page.getByRole("textbox", {name: "Card number"}).fill("4111111111111111");

        await this.page.getByRole("textbox", {name: "Expiry"}).fill("1233");

        await this.page.getByRole("textbox", {name: "CVV"}).fill("123");

        await this.page.getByRole("button", {name: /Pay/i}).click();
    }

}