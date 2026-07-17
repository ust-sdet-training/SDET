import { Page } from "@playwright/test";

export class WaitHelper {

    static async waitForPageLoad(page: Page) {
        await page.waitForLoadState("networkidle");
    }

}