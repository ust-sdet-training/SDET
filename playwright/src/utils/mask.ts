import { Locator, Page } from "@playwright/test";

const DEFAULT_MASKS = [

    "input[type='password']",

    "input[name='password']",

    "input[name='token']",

    "input[name='otp']",

    "input[name='cvv']",

    "input[name='cardNumber']",

    "[data-sensitive]",

    "[data-mask]"

];

export async function getMaskedLocators(

    page: Page,

    additionalLocators: (string | Locator)[] = []

): Promise<Locator[]> {

    const masks: Locator[] = [];

    for (const selector of DEFAULT_MASKS) {

        const locator = page.locator(selector);

        if (await locator.count()) {

            masks.push(locator);

        }

    }

    for (const item of additionalLocators) {

        if (typeof item === "string") {

            const locator = page.locator(item);

            if (await locator.count()) {

                masks.push(locator);

            }

        } else {

            masks.push(item);

        }

    }

    return masks;

}