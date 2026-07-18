import { Page } from "@playwright/test";

export class BusPage {

    constructor(private page: Page) {}

   async searchBus(
    from:string,
    to:string,
    date:string
)
 {

        await this.page.getByRole("link", { name: "Buses" }).click();

        await this.page.getByRole("combobox", { name: "From" }).fill(from);

        await this.page.getByRole("combobox", { name: "To" }).fill(to);

        await this.page.getByRole("textbox", { name: "Date of journey" }).fill(date);

        await this.page.getByRole("button", { name: "Search Buses" }).click();
    }

    async selectSeat() {

        await this.page.getByRole("button", { name: "Select Seats" }).first().click();

        await this.page.locator("[data-state='available']").first().click();

        await this.page.getByRole("button", {name: "Continue to passenger details"}).click();
    }

}