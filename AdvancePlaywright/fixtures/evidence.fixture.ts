import { test as base, expect } from "./diagnostic.fixture";

export const test = base.extend<{
    evidence: Record<string, any>;
}>({

    evidence: async ({}, use, testInfo) => {

        const evidence: Record<string, any> = {};

        await use(evidence);

        // Attach all collected evidence if the test fails
        if (testInfo.status !== testInfo.expectedStatus) {

            for (const key in evidence) {

                await testInfo.attach(`${key}.json`, {
                    body: JSON.stringify(evidence[key], null, 2),
                    contentType: "application/json"
                });

            }

        }

    }

});

export { expect };