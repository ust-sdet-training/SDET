import { Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { logger } from "../utils/Logger";

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
        logger.info("[HomePage] Searching for a flight");
        await this.fill(this.fromTextbox, from, "departure city");
        await this.page.getByRole("option").first().click();
        await this.fill(this.toTextbox, to, "destination city");
        await this.page.getByRole("option").first().click();
        await this.fill(this.dateTextbox, date, "travel date");
        await this.click(this.searchButton, "Search button");
    }
}