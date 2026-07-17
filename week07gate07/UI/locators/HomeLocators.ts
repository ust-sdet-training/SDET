import { Locator, Page } from "@playwright/test";

export class HomeLocators {

    readonly flightsTab: Locator;
    readonly busesTab: Locator;

    readonly fromTextBox: Locator;
    readonly toTextBox: Locator;

    readonly datePicker: Locator;

    readonly searchButton: Locator;

    readonly swapButton: Locator;

    readonly myTripsLink: Locator;

    readonly logoutLink: Locator;

    constructor(private readonly page: Page) {

        this.flightsTab = page.getByRole("tab", {
            name: "Flights"
        });

        this.busesTab = page.getByRole("tab", {
            name: "Buses"
        });

        this.fromTextBox = page.locator("#home-from");

        this.toTextBox = page.locator("#home-to");

        this.datePicker = page.locator("#home-date");

        this.searchButton = page.getByRole("button", {
            name: "Search"
        });

        this.swapButton = page.locator("#home-swap");

        this.myTripsLink = page.getByRole("link", {
            name: "My Trips"
        });

        this.logoutLink = page.getByRole("link", {
            name: "Log out"
        });

    }
    fromOption(code: string): Locator {
    return this.page.locator(`#home-from-opt-${code}`);
}

toOption(code: string): Locator {
    return this.page.locator(`#home-to-opt-${code}`);
}

}