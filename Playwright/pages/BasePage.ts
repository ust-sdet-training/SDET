import { Locator, Page, expect } from "@playwright/test";

export class BasePage {

    constructor(protected page: Page) {
    }

    async click(locator: Locator) {

        await locator.click();

    }

    async fill(locator: Locator, value: string) {

        await locator.fill(value);

    }

    async type(locator: Locator, value: string) {

        await locator.pressSequentially(value);

    }

    async visible(locator: Locator) {

        await expect(locator).toBeVisible();

    }

    async screenshot(name: string) {

        await this.page.screenshot({

            path: `screenshots/${name}.png`,

            fullPage: true

        });

    }

}