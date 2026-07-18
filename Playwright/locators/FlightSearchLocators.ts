import { Locator, Page } from "@playwright/test";

export class FlightSearchLocators {

    readonly fromDropdown: Locator;

    readonly toDropdown: Locator;

    readonly dateTextbox: Locator;

    readonly searchButton: Locator;

    constructor(page: Page) {

        this.fromDropdown = page.getByRole("combobox", {
            name: "From"
        });

        

        this.toDropdown = page.getByRole("combobox", {
            name: "To"
        });


        this.dateTextbox = page.getByRole("textbox", {
            name: "Date"
        });

        this.searchButton = page.getByRole("button", {
            name: "Search"
        });

    }
}