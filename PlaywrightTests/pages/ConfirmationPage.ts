import { expect, Page } from "@playwright/test";

/**
* This page contains all the locators and all the actions for booking confirmation.
*/
export class ConfirmationPage {
    constructor(private readonly page:Page) {}

    async checkPnrVisible(){
        await expect(this.page.locator('div.pnr[data-id="pnr"]')).toBeVisible();
    }

    async checkAllSetHeadingVisible(){
        await expect(this.page.getByRole('heading', { name: "You're all set!" })).toBeVisible();
    }

    async checkConfirmationDetailsVisible(){
        await this.checkPnrVisible();
        await this.checkAllSetHeadingVisible();
    }
}
