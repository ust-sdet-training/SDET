import { Locator, Page } from "@playwright/test";

export class BookingLocators {

    readonly bookButton: Locator;

    constructor(page: Page) {

        this.bookButton = page.getByRole("button", {name: "Book"});

    }

}