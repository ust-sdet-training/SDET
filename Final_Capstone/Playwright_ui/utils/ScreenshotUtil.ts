import { Page } from "@playwright/test";

export class ScreenshotUtil {

    static async capture(page: Page, fileName: string) {

        await page.screenshot({

            path: `evidence/screenshots/${fileName}.png`,

            fullPage: true

        });

    }

}