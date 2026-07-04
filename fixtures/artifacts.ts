import { expect, test as base } from './diagnose-log';

type ArtifactEvidence = {
   apiResponse?: unknown;
   diagnosis?: string;
   requestData?: unknown;
   screenshot?: Buffer;
   cartData?: unknown;
};

export const test = base.extend<{ evidence: ArtifactEvidence }>({
    evidence: async ({}, use, testInfo) => {
        const evidence: ArtifactEvidence = {};

        await use(evidence); 

      if (true) {
    try {
        const payload = evidence.apiResponse ?? { error: "No cart response was captured before failure." };
        await testInfo.attach("api-response.json", {
            
        body: JSON.stringify(payload, null, 2),
        contentType: "application/json"

        });
    } catch (err) {
        console.error("Failed to attach api-response.json:", err);
    }

    try {
        const payload = evidence.requestData ?? { error: "No api request was captured before failure." };
        await testInfo.attach("api-request-data.json", {
            
        body: JSON.stringify(payload, null, 2),
        contentType: "application/json"

        });
    } catch (err) {
        console.error("Failed to attach api-request.json:", err);
    }

    
    try {
        if (evidence.screenshot) {
            await testInfo.attach("success-screenshot.png", {
                body: evidence.screenshot,
                contentType: "image/png"
            });
        }
    } catch (err) {
        console.error("Failed to attach screenshot:", err);
    }

     try {
        const payload = evidence.cartData ?? { error: "No cart data was captured before failure." };
        await testInfo.attach("cart-data.json", {
            
        body: JSON.stringify(payload, null, 2),
        contentType: "application/json"

        });
    } catch (err) {
        console.error("Failed to attach api-request.json:", err);
    }


    try {
        await testInfo.attach("diagnosis.txt", {
            body: evidence.diagnosis ?? "No diagnosis captured before failure.",
            contentType: "text/plain"
        });
    } catch (err) {
        console.error("Failed to attach diagnosis.txt:", err);
    }
        }
    }
});

export { expect };