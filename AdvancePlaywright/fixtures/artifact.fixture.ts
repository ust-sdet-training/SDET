import { test as base, expect } from "@playwright/test";

type Evidence = {
    cartResponse?: any;
    diagnosis?: string;
};

export const test = base.extend<{ evidence: Evidence }>({

    evidence: async ({}, use, testInfo) => {

        const evidence: Evidence = {};

        await use(evidence);

        // Attach only if the test fails
        if (testInfo.status !== testInfo.expectedStatus) {

            if (evidence.cartResponse) {
                await testInfo.attach("cart-response.json", {
                    body: JSON.stringify(evidence.cartResponse, null, 2),
                    contentType: "application/json"
                });
            }

            if (evidence.diagnosis) {
                await testInfo.attach("diagnosis.txt", {
                    body: evidence.diagnosis,
                    contentType: "text/plain"
                });
            }

        }

    }

});

export { expect };