import { Page } from "@playwright/test";
import { TicketLocators } from "../locators/TicketLocators";
import { expect } from "../fixtures/test";

export class TicketPage {

    private readonly locators: TicketLocators;

    constructor(private readonly page: Page) {
        this.locators = new TicketLocators(page);
    }


    async verifyTicket(): Promise<void> {
        await expect(this.locators.pnr()).toBeVisible();
        await expect(this.locators.verifyPage()).toBeVisible();
        await expect(this.locators.viewButton()).toBeVisible();
}

}