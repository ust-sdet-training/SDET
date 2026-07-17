import { test as diagnosticTest, expect } from "./diagnostic.fixture";

type EvidenceFixture = {
    evidence: (name: string, body: unknown) => Promise<void>;
};

export const test = diagnosticTest.extend<EvidenceFixture>({
    evidence: async ({}, use, testInfo) => {

        await use(async (name: string, body: unknown) => {
            await testInfo.attach(name, {
                body: JSON.stringify(body, null, 2),
                contentType: "application/json"
            });
        });

    }
});

export { expect };