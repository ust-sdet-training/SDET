import { APIRequestContext, Page, expect } from "@playwright/test";
import crypto from "node:crypto";

const API_URL = process.env.API_BASE_URL || "http://localhost:4000";

const headers = {
    Authorization: "Bearer demo-token-1-customer",
    "Content-Type": "application/json"
};


// Create a new order that will be used for refund testing
export async function seedOrder(request: APIRequestContext) {

    const response = await request.post(
        `${API_URL}/api/refund-lab/orders`,
        {
            headers,
            data: {
                taxPaise: 4995,
                lines: [
                    {
                        sku: "TEE",
                        name: "Training Tee",
                        unitPaise: 33300,
                        qty: 3
                    }
                ]
            }
        }
    );

    // Verify order is created successfully
    expect(response.ok()).toBeTruthy();

    return await response.json();
}


// Refund the requested quantity of an item

export async function refundOrder(
    request: APIRequestContext,
    orderId: number,
    qty: number,
    idempotencyKey: string
) {

    return await request.post(
        `${API_URL}/api/refunds`,
        {
            headers: {
                ...headers,
                "Idempotency-Key": idempotencyKey
            },

            data: {
                orderId,
                lines: [
                    {
                        sku: "TEE",
                        qty
                    }
                ]
            }
        }
    );

}

// Check whether a refund request is allowed before refunding
export async function checkRefund(
    request: APIRequestContext,
    orderId: number,
    sku: string,
    qty: number
) {

    const response = await request.post(
        `${API_URL}/api/refunds/check`,
        {
            headers,
            data: {
                orderId,
                lines: [
                    {
                        sku,
                        qty
                    }
                ]
            }
        }
    );

    // Verify request is successful
    expect(response.ok()).toBeTruthy();

    return await response.json();

}

// Fetch the latest order details after refund

export async function getLedger(
    request: APIRequestContext,
    orderId: number
) {

    const response = await request.get(
        `${API_URL}/api/refund-lab/orders/${orderId}`,
        {
            headers
        }
    );

    expect(response.ok()).toBeTruthy();

    return await response.json();

}


// Open Returns page for the given order

export async function openReturnsPage(
    page: Page,
    orderId: number
) {

    await page.goto(`/returns?orderId=${orderId}`);

}


// Generate a unique Idempotency Key
// Used to prevent duplicate refunds
export function generateIdempotencyKey(prefix = "refund") {

    return `${prefix}-${crypto.randomUUID()}`;

}