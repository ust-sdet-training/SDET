import {expect, test as base} from "@playwright/test";
import {HomeFlow} from "../flows/HomeFlow";

type TestFlow = {
    homeFlow: HomeFlow;
}

export const test = base.extend<TestFlow>({
    homeFlow: async ({page}, use) => {
        await use(new HomeFlow(page));
    }
});

export {expect};