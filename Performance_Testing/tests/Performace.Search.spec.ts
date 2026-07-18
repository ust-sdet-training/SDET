import { test, expect } from "@playwright/test";
import { TripStackFlow } from "../flow/TripStackFlow";
import { Env } from "../../Ui_Testing/config/Env";

test("Search Performance", async ({ page }) => {

    const flow = new TripStackFlow(page);
    const start = Date.now();
    await flow.login(process.env.TRIPSTACK_USERNAME!,process.env.SHOPKART_PASSWORD!);
    await flow.search("hyderabad","kolkata","2026-07-29");

    const duration = Date.now() - start;

    console.log(`Search completed in ${duration} ms`);

    expect(duration).toBeLessThan(3000);
});