import { Page } from "@playwright/test";

export class HomeLocators {

    constructor(private page: Page) {}

    busesTab = () =>
    this.page.getByRole("tab", { name: "Buses" });

    fromInput = () =>
        this.page.locator("#home-from");

    toInput = () =>
        this.page.locator("#home-to");

    dateInput = () =>
        this.page.locator("#home-date");

    swapButton = () =>
        this.page.locator("#bus-swap");

    searchButton = () =>
        this.page.getByRole("button", { name: "Search" });
}