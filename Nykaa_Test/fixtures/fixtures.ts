import { test as base } from "@playwright/test";
import { TestFlow } from "../flow/TestFlow";

type MyFixture = {
    flow: TestFlow;
};

export const test = base.extend<MyFixture>({
    flow: async ({ page }, use) => {
        await use(new TestFlow(page));
    }
});

export { expect } from "@playwright/test";