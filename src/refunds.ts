import { APIRequestContext, expect } from "@playwright/test";

 
const posApiUrl = process.env.POS_API_URL || 'http://localhost:4000';
export async function refunds(
  request: APIRequestContext,
  id: number,
  qty: number,
  sku: string,
  idempotencyKey: string
) {
  const response = await request.post(`${posApiUrl}/api/refunds`, {
    headers: {
      Authorization: "Bearer demo-token-1-customer",
      "Content-Type": "application/json",
      "Idempotency-Key": idempotencyKey
    },
    data: {
      orderId: id,
      lines: [
        {
          sku,
          qty
        }
      ]
    }
  });

  return response;
}