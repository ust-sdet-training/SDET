import { APIRequestContext, Page, expect } from "@playwright/test";
import crypto from "node:crypto";

const API_BASE_URL = process.env.API_BASE_URL || "http://localhost:4000";
const BASE_URL = process.env.BASE_URL || "http://localhost:5173";
const AUTH_TOKEN = process.env.AUTH_TOKEN || "demo-token-1-customer";

const defaultHeaders = {
  Authorization: `Bearer ${AUTH_TOKEN}`,
  "Content-Type": "application/json",
};

export type SeedOrderOptions = {
  purchaseDaysAgo?: number;
  finalSale?: boolean;
  returnable?: boolean;
};

export async function createOrder(
  request: APIRequestContext,
  options: SeedOrderOptions = {}
) {
  const response = await request.post(`${API_BASE_URL}/api/refund-lab/orders`, {
    headers: defaultHeaders,
    data: {
      taxPaise: 4995,
      ...(options.purchaseDaysAgo !== undefined && { daysAgo: options.purchaseDaysAgo }),
      lines: [
        {
          sku: "TEE",
          name: "Training Tee",
          unitPaise: 33300,
          qty: 3,
          ...(options.finalSale !== undefined && { finalSale: options.finalSale }),
          ...(options.returnable !== undefined && { nonReturnable: !options.returnable }),
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
  idempotencyKey?: string
) {
  return request.post(`${API_BASE_URL}/api/refunds`, {
    headers: {
      ...defaultHeaders,

      // Add the header only when a key is provided.
      ...(idempotencyKey && {
        "Idempotency-Key": idempotencyKey,
      }),
    },
    data: {
      orderId,
      lines: [{ sku: "TEE", qty }],
    },
  });
}

export async function checkRefund(
  request: APIRequestContext,
  orderId: number,
  sku: string,
  qty: number
) {
  const response = await request.post(`${API_BASE_URL}/api/refunds/check`, {
    headers: defaultHeaders,
    data: {
      orderId,
      lines: [{ sku, qty }],
    },
  });
  expect(response.ok()).toBeTruthy();
  return response.json();
}

export async function getLedger(request: APIRequestContext, orderId: number) {
  const response = await request.get(`${API_BASE_URL}/api/refund-lab/orders/${orderId}`, {
    headers: defaultHeaders,
  });
  expect(response.ok()).toBeTruthy();
  return response.json();
}

export async function openReturnsPage(page: Page, orderId: number) {
  await page.goto(`${BASE_URL}/returns?orderId=${orderId}`);
}

export function generateIdempotencyKey(prefix = "refund") {
  return `${prefix}-${crypto.randomUUID()}`;
}