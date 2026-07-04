import { expect, test } from "../fixtures/artifact-test";

const posApiUrl = process.env.POS_API_URL || "http://localhost:4000";

const authHeaders = {
  Authorization: "Bearer demo-token-1-customer"
};


test(
  "publishes screenshot, video, trace and cart payload for checkout failure",
  async ({ page, request, evidence }) => {
    // Create a unique cart session for this test run
    const cartSession = `w5d3-artifact-${Date.now()}`;

    await page.goto("/debug-lab");

    await page.getByRole("button", {
      name: "Refresh cart total"
    }).click();

    // Make sure the page is ready before continuing
    await expect(
      page.getByTestId("debug-status")
    ).toHaveText("ready");

    const response = await request.get(`${posApiUrl}/api/cart`, {
      headers: {
        ...authHeaders,
        "X-Cart-Session": cartSession
      }
    });

    // Store API response so it can be attached if the test fails
    evidence.cartResponse = await response.json();

    evidence.diagnosis = [
      "Week 5 Day 3 - Artifact Demo",
      `Cart Session : ${cartSession}`,
      `Items : ${
        (evidence.cartResponse as { items?: unknown[] }).items?.length ?? 0
      }`,
      "Intentional assertion failure to generate Screenshot, Trace, Video and Attachments."
    ].join("\n");

    const screenshot = await page.screenshot({ fullPage: true });

  await test.info().attach("failure-screenshot", {
  body: screenshot,
  contentType: "image/png",
});

    // Intentional failure to collect test artifacts
    // await expect(
    //   page.getByTestId("debug-cart-total")
    // ).toHaveText("Rs. 999999");
  }
);