import { Locator, Page } from "@playwright/test";

export class BasePage {

    constructor(protected page: Page) {}

    async navigate(url: string) {
        await this.page.goto(url);
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

    async getText(locator: Locator) {
        return await locator.textContent();
    }

    async isVisible(locator: Locator) {
        return await locator.isVisible();
    }

    async waitFor(locator: Locator) {
        await locator.waitFor();
    }

    async getTitle() {
        return await this.page.title();
    }

}