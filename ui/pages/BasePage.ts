import { expect, Locator, Page } from '@playwright/test';

export class BasePage {

    constructor(protected page: Page) {}

    async navigate(url: string) {
        await this.page.goto(url);
    }

    async click(locator: Locator) {
        await locator.waitFor({ state: 'visible' });
        await locator.click();
    }

    async fill(locator: Locator, value: string) {
        await locator.waitFor({ state: 'visible' });
        await locator.fill(value);
    }

    async select(locator: Locator, value: string) {
        await locator.selectOption(value);
    }

    async getText(locator: Locator): Promise<string> {
        return (await locator.textContent())?.trim() ?? '';
    }

    async isVisible(locator: Locator) {
        await expect(locator).toBeVisible();
    }

    async verifyUrl(url: RegExp) {
        await expect(this.page).toHaveURL(url);
    }

    async waitForLoad() {
        await this.page.waitForLoadState('networkidle');
    }

    async screenshot(name: string) {
        await this.page.screenshot({
            path: `screenshots/${name}.png`,
            fullPage: true
        });
    }
}