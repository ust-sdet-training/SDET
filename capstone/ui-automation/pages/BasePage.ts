import { Locator, Page } from "@playwright/test";
import { logger } from "../utils/Logger";

export class BasePage {

    constructor(protected page: Page) {}

    protected logAction(message: string) {
        logger.info(`[${this.constructor.name}] ${message}`);
    }

    async navigate(url: string) {
        this.logAction(`Navigating to ${url}`);
        await this.page.goto(url);
    }

    async click(locator: Locator, actionName = "element") {
        this.logAction(`Clicking ${actionName}`);
        await locator.click();
    }

    async fill(locator: Locator, value: string, fieldName = "field") {
        this.logAction(`Filling ${fieldName}`);
        await locator.fill(value);
    }

    async type(locator: Locator, value: string, fieldName = "field") {
        this.logAction(`Typing into ${fieldName}`);
        await locator.pressSequentially(value);
    }

    async getText(locator: Locator) {
        this.logAction("Getting text from element");
        return await locator.textContent();
    }

    async isVisible(locator: Locator) {
        this.logAction("Checking element visibility");
        return await locator.isVisible();
    }

    async waitFor(locator: Locator, elementName = "element") {
        this.logAction(`Waiting for ${elementName}`);
        await locator.waitFor();
    }

    async getTitle() {
        this.logAction("Getting page title");
        return await this.page.title();
    }

}