import { APIRequestContext, Page, expect } from "@playwright/test";
import crypto from "node:crypto";

const API_BASE_URL = process.env.API_BASE_URL || "http://localhost:4000";
const BASE_URL = process.env.BASE_URL || "http://localhost:5173";
const AUTH_TOKEN = process.env.AUTH_TOKEN || "demo-token-1-customer";

const defaultHeaders = {
  Authorization: `Bearer ${AUTH_TOKEN}`,
  "Content-Type": "application/json",
};

export async function seedOrder(request: APIRequestContext) {
  // Creates a predictable order used by refund test scenarios.
  const response = await request.post(`${API_BASE_URL}/api/refund-lab/orders`, {
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
  });

  expect(response.ok()).toBeTruthy();

  return response.json();
}

export async function refundOrder(
  request: APIRequestContext,
  orderId: number,
  qty: number,
  idempotencyKey: string,
) {
  return request.post(`${API_BASE_URL}/api/refunds`, {
    headers: {
      ...defaultHeaders,
      // Ensures duplicate requests are processed only once.
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

export async function checkRefund(
  request: APIRequestContext,
  orderId: number,
  sku: string,
  qty: number,
) {
  // Checks refund eligibility without making the refund...
  const response = await request.post(`${API_BASE_URL}/api/refunds/check`, {
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
  });

  expect(response.ok()).toBeTruthy();

  return response.json();
}

export async function getLedger(request: APIRequestContext, orderId: number) {
  const response = await request.get(
    `${API_BASE_URL}/api/refund-lab/orders/${orderId}`,
    {
      headers: defaultHeaders,
    },
  );

  expect(response.ok()).toBeTruthy();

  return response.json();
}

export async function openReturnsPage(page: Page, orderId: number) {
  await page.goto(`${BASE_URL}/returns?orderId=${orderId}`);
}

export function generateIdempotencyKey(prefix = "refund") {
  // To run every test unique we are using the UUID.
  return `${prefix}-${crypto.randomUUID()}`;
}
