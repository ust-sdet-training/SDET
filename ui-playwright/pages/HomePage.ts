import { Locator, Page } from "@playwright/test";

export class HomePage {

    private from: Locator;
    private to: Locator;
    private date: Locator;
    private search: Locator;

    constructor(private readonly page: Page) {

        this.from = page.getByRole("combobox", { name: "From" });
        this.to = page.getByRole("combobox", { name: "To" });
        this.date = page.getByRole("textbox", { name: "Date" });
        this.search = page.getByRole("button", { name: "Search" });
    }

    async searchFlight(
        from: string,
        fromAirport: string,
        to: string,
        toAirport: string,
        date: string
    ) {

        await this.from.fill(from);
        await this.page.getByRole("option", { name: fromAirport }).click();

        await this.to.fill(to);
        await this.page.getByRole("option", { name: toAirport }).click();

        await this.date.fill(date);

        await this.search.click();
    }
}