import { expect, test as base } from '@playwright/test';
 
type ArtifactEvidence = {
    response?: unknown;
    diagnosis?: string;
};
 
export const test = base.extend<{ evidence: ArtifactEvidence }>({
    evidence: async ({}, use, testInfo) => {
        const evidence: ArtifactEvidence = {};
        await use(evidence);
        if (testInfo.status !== testInfo.expectedStatus) {
            return;
        }
 
        if(Object.prototype.hasOwnProperty.call(evidence, "response")){
            await testInfo.attach("response.ndjson", {
                body: JSON.stringify({response:evidence.response}),
                contentType: "application/x-ndjson"
            });
        }
    }
});

export { expect };