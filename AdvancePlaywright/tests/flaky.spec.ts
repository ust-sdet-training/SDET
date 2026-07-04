import { test, expect } from "@playwright/test";

test.describe("Flaky Test Demo", () => {

    test("STABLE - Cart total after UI synchronization", async () => {

        const apiTotal = 4499;

        const randomDelay = Math.floor(Math.random() * 1000);

        await new Promise(resolve => setTimeout(resolve, randomDelay));

        // Wait until the UI is considered updated
        if (randomDelay < 300) {
            await new Promise(resolve => setTimeout(resolve, 400));
        }

        const uiTotal = 4499;

        expect(uiTotal).toBe(apiTotal);

    });

});