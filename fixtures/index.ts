import { test as base, expect } from "@playwright/test";
import { HomeFlow } from "../flows/HomeFlow";

type TestFlow = {
    home: HomeFlow;
};

export const test = base.extend<TestFlow>({
    home: async ({ page }, use) => {
        await use(new HomeFlow(page));
    }
});

export { expect };