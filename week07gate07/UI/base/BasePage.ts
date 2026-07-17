import { expect, Locator, Page } from "@playwright/test";

export abstract class BasePage {
    protected readonly page: Page;
    constructor(page: Page) {
        this.page = page;
    }
    async click(locator: Locator): Promise<void> {
        await locator.waitFor({ state: "visible" });
        await locator.click();
    }
    async fill(locator: Locator, value: string): Promise<void> {
        await locator.waitFor({ state: "visible" });
        await locator.fill(value);
    }
    async type(locator: Locator, value: string): Promise<void> {
        await locator.waitFor({ state: "visible" });
        await locator.clear();
        await locator.type(value);
    }
    async pressEnter(): Promise<void> {
        await this.page.keyboard.press("Enter");
    }
    async waitForPage(): Promise<void> {
        await this.page.waitForLoadState("networkidle");
    }
    async verifyUrlContains(text: string): Promise<void> {
        await expect(this.page).toHaveURL(new RegExp(text));
    }
    async isVisible(locator: Locator): Promise<void> {
        await expect(locator).toBeVisible();
    }
    async scroll(locator: Locator): Promise<void> {
        await locator.scrollIntoViewIfNeeded();
    }

}