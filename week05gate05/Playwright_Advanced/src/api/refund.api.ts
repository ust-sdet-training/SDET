import { APIRequestContext, Page, expect } from "@playwright/test";
import crypto from "node:crypto";

//  Read API, UI URLs and authentication token from environment variables
const API_BASE_URL = process.env.API_BASE_URL || "http://localhost:4000";
const BASE_URL = process.env.BASE_URL || "http://localhost:5173";
const AUTH_TOKEN = process.env.AUTH_TOKEN || "demo-token-1-customer";

const defaultHeaders = {
  Authorization: `Bearer ${AUTH_TOKEN}`,
  "Content-Type": "application/json",
};
// Create (Seed) a test order before running tests
export async function seedOrder(request: APIRequestContext) {
  const response = await request.post(
    `${API_BASE_URL}/api/refund-lab/orders`,
    {
      headers: defaultHeaders,
      data: {
        taxPaise: 4995,
        lines: [
          {
            sku: "TEE",
            name: "Training Tee",
            unitPaise: 33300,
            qty: 3,
          },
        ],
      },
    }
  );

  expect(response.ok()).toBeTruthy();

  return response.json();
}

// Send a refund request for the specified order
export async function refundOrder(
  request: APIRequestContext,
  orderId: number,
  qty: number,
  idempotencyKey: string
) {
  return request.post(`${API_BASE_URL}/api/refunds`, {
    headers: {
      ...defaultHeaders,
       // Prevent duplicate refund processing
      "Idempotency-Key": idempotencyKey,
    },
    data: {
      orderId,
      lines: [
        {
          sku: "TEE",
          qty,
        },
      ],
    },
  });
}
// Validate whether the requested refund is allowed

export async function checkRefund(
  request: APIRequestContext,
  orderId: number,
  sku: string,
  qty: number
) {
  const response = await request.post(
    `${API_BASE_URL}/api/refunds/check`,
    {
      headers: defaultHeaders,
      data: {
        orderId,
        lines: [
          {
            sku,
            qty,
          },
        ],
      },
    }
  );

  expect(response.ok()).toBeTruthy();

  return response.json();
}
// Retrieve updated refund ledger after refund processing
export async function getLedger(
  request: APIRequestContext,
  orderId: number
) {
  const response = await request.get(
    `${API_BASE_URL}/api/refund-lab/orders/${orderId}`,
    {
      headers: defaultHeaders,
    }
  );

  expect(response.ok()).toBeTruthy();

  return response.json();
}
// Open the Returns page for the given order in the browser
export async function openReturnsPage(
  page: Page,
  orderId: number
) {
  await page.goto(`${BASE_URL}/returns?orderId=${orderId}`);
}

// Generate a unique Idempotency Key for every refund request
export function generateIdempotencyKey(prefix = "refund") {
  return `${prefix}-${crypto.randomUUID()}`;
}