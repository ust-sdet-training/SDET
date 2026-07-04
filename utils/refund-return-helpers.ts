import { APIRequestContext, expect } from "@playwright/test";

export const posApiUrl = process.env.POS_API_URL || "http://localhost:4000";

export const authHeaders = {
    "Authorization": "Bearer demo-token-1-customer",
    "Content-Type": "application/json"
};

export async function seedOrder(
    request: APIRequestContext, 
    order?: object)
    {
        const response = await request
        .post(`${posApiUrl}/api/refund-lab/orders`, {
            headers: authHeaders,
            data: 
            order ??
            {
                taxPaise: 4995,
                lines: [{sku: 'TEE', 
                    name: 'Training Tee',
                    unitPaise: 33300, 
                    qty: 3
                }]
            },
        });

        if(!response.ok()){
            // log.info(`Failed to create order: ${response.status()});
        }

        expect(response.ok()).toBeTruthy();
        
        return await response.json();
    }

export async function createRefund(
    request: APIRequestContext, 
    orderId: number, 
    // lines: "ALL" | 
    lines:  Array< {sku: string; qty: number}>, 
    idempotencyKey?: string,
    key= crypto.randomUUID()){
        return request.post(`${posApiUrl}/api/refunds`, {
        headers: {
            ...authHeaders,
            "Idempotency-Key": idempotencyKey?? key
        },
        data: { orderId, lines }
        });
    }

export async function checkRefund(
    request: APIRequestContext, 
    orderId: number, 
    // lines: "ALL" | 
    lines:  Array< {sku: string; qty: number}>){
        return request.post(`${posApiUrl}/api/refunds/check`, {
        headers: {
            ...authHeaders
        },
        data: { orderId, lines }
        });
    }