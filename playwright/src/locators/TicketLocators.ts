import { Locator, Page } from "@playwright/test";

export class TicketLocators {

    constructor(private readonly page: Page) {}

    verifyPage(): Locator {
        return this.page.getByText("CONFIRMED", { exact: true});
    }

    pnr(): Locator {
        return this.page.locator(".pnr")
    }

    viewButton(): Locator{
        return this.page.getByRole("button", {name: "View my trips"});
    }

}