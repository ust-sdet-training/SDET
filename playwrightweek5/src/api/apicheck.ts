import { APIRequestContext, expect } from "@playwright/test";

export async function apiCheck(
  request: APIRequestContext,
  sku: string,
  orderId: number,
  qty:number

) {
  const response = await request.post('http://localhost:4000/api/refunds/check', {
        headers: {
            Authorization: 'Bearer demo-token-1-customer',
            'Content-Type': 'application/json'
        },
        data: {
            orderId: orderId,
            lines: [
            {
                sku: sku,
                qty: qty,
            }
            ]
        }
        });

  return response;
}