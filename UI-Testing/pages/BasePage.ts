import { expect, Locator, Page } from "@playwright/test"
import { AppLogger, logger } from "../utils/Logger"

export class BasePage {
    constructor(protected page: Page, protected log: AppLogger) {}

    async open(url: string): Promise<void> {
        logger.info(`Opening url: ${url}`)
        await this.page.goto(url)
        await this.page.waitForLoadState()
    }

    async click(locator: Locator): Promise<void> {
        logger.info("Clicking element")
        await locator.click()
    }

    async fill(locator: Locator, value: string): Promise<void> {
        logger.info(`Entering value: ${value}`)
        await locator.fill(value)
    }

    async getText(locator: Locator): Promise<string> {
        return (await locator.textContent())?.trim() ?? ""
    }

    async isVisible(locator: Locator): Promise<void> {
        await expect(locator).toBeVisible()
    }

    // async waitForPageLoad(): Promise<void> {
    //     await this.page.waitForLoadState("networkidle")
    // }

    async takeScreenshot(name: string): Promise<void> {
        logger.info(`Capturing Screenshot ${name}`)

        await this.page.screenshot({
            fullPage: true
        })
    }
}