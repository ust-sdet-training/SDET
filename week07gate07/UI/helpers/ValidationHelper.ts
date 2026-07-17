import { expect, Locator, Page } from "@playwright/test";

export class ValidationHelper {

    static async text(locator: Locator, expected: string) {

        await expect(locator).toContainText(expected);

    }

    static async visible(locator: Locator) {

        await expect(locator).toBeVisible();

    }

    static async title(page: Page, title: string) {

        await expect(page).toHaveTitle(new RegExp(title));

    }

    static async url(page: Page, url: string) {

        await expect(page).toHaveURL(new RegExp(url));

    }

}