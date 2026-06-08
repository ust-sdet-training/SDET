import { expect, test } from "@playwright/test";

const posApiUrl = process.env.POS_API_URL || "http://localhost:4000";

test("retail API health check is available", async ({ request }) => {
  const response = await request.get(`${posApiUrl}/api/health`);
  const body = await response.json();

  expect(response.ok()).toBeTruthy();
  expect(body).toEqual({ status: "ok", service: "sdet-retail-app" });
});
