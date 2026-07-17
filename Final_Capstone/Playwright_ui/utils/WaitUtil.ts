import { Locator } from "@playwright/test";

export class WaitUtil {

    static async visible(locator: Locator) {

        await locator.waitFor({

            state: 'visible'

        });

    }

    static async hidden(locator: Locator) {

        await locator.waitFor({

            state: 'hidden'

        });

    }

}