import { expect, test as base } from '@playwright/test';
 
type ArtifactEvidence = {
    cartResponse?: unknown;
    diagnosis?: string;
};
 
export const test = base.extend<{ evidence: ArtifactEvidence }>({
    evidence: async ({}, use, testInfo) => {
        const evidence: ArtifactEvidence = {};
        await use(evidence);
        if (testInfo.status !== testInfo.expectedStatus) {
            return;
        }
 
        if(Object.prototype.hasOwnProperty.call(evidence, "cartResponse")){
            await testInfo.attach("cart-response.ndjson", {
                body: JSON.stringify({cartResponse:evidence.cartResponse}),
                contentType: "application/x-ndjson"
            });
        }
 
        if(evidence.diagnosis){
            await testInfo.attach("diagnosis.txt", {
                body: evidence.diagnosis,
                contentType: "text/plain"
            });
        }
    }
});
export { expect };