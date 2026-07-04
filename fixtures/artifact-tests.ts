import { expect, test as base } from '@playwright/test';

type ArtifactEvidence = {
    cartResponse?: unknown;
    diagnosis?: string;
    logs?: string[];
};

export const test = base.extend<{ evidence: ArtifactEvidence }>({
    evidence: async ({}, use, testInfo) => {

        const evidence: ArtifactEvidence = {
            logs: []
        };

        await use(evidence);

        await testInfo.attach("cart-response.json", {
            body: JSON.stringify(
                evidence.cartResponse ??
                { message: "No cart captured" },
                null,
                2
            ),
            contentType: "application/json"
        });

        await testInfo.attach("diagnosis.txt", {
            body: evidence.diagnosis ?? "No diagnosis captured",
            contentType: "text/plain"
        });

        await testInfo.attach("logs.txt", {
            body: (evidence.logs ?? []).join("\n"),
            contentType: "text/plain"
        });
    }
});

export { expect };