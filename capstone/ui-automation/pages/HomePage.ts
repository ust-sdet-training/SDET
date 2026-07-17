import { Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";

export class HomePage extends BasePage {

    readonly fromTextbox: Locator;
    readonly toTextbox: Locator;
    readonly dateTextbox: Locator;
    readonly searchButton: Locator;

    constructor(page: Page) {

        super(page);
        this.fromTextbox = page.getByRole("combobox", {name: "From"});
        this.toTextbox = page.getByRole("combobox", {name: "To"});
        this.dateTextbox = page.getByRole("textbox", {name: "Date"});
        this.searchButton = page.getByRole("button", {name: "Search"});
    }

    async searchFlight(from: string, to: string, date: string) {

    await this.fill(this.fromTextbox, from);
    await this.page.getByRole("option").first().click();
    await this.fill(this.toTextbox, to);
    await this.page.getByRole("option").first().click();
    await this.fill(this.dateTextbox, date);
    await this.click(this.searchButton);

}
}