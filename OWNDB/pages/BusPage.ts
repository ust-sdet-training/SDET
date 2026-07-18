import { Page } from "@playwright/test";

export class BusPage {

    constructor(private page: Page) {}

    async searchBus() {

        await this.page.getByRole("link", { name: "Buses" }).click();

        await this.page.getByRole("combobox", { name: "From" }).fill("BOM");

        await this.page.getByRole("combobox", { name: "To" }).fill("PUN");

        await this.page.getByRole("textbox", { name: "Date of journey" }).fill("2026-07-20");

        await this.page.getByRole("button", { name: "Search Buses" }).click();
    }

    async selectSeat() {

        await this.page.getByRole("button", { name: "Select Seats" }).click();

        await this.page.locator("[data-state='available']").first().click();

        await this.page.getByRole("button", {name: "Continue to passenger details"}).click();
    }

}