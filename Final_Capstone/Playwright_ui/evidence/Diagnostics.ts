import { Page } from "@playwright/test";

export class Diagnostics {

    static async collect(page: Page, testName: string) {

        await page.screenshot({

            path: `diagnostics/${testName}.png`,

            fullPage: true

        });

        const html = await page.content();

        const fs = require('fs');

        fs.writeFileSync(

            `diagnostics/${testName}.html`,

            html

        );

    }

}