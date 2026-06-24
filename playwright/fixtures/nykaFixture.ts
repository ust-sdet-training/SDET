import { test as base } from "@playwright/test";
import { NykaaFlow } from "../flows/nykaFlow";

export const test = base.extend<{ nykaa: NykaaFlow }>({

    nykaa: async ({ page }, use) => {
        await use(new NykaaFlow(page));
    }

});

export { expect } from "@playwright/test";