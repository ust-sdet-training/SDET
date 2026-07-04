import { APIRequestContext, expect } from "@playwright/test";

const apiURL =process.env.POS_API_URL || 'http://localhost:4000';

export async function getLedger(
  request: APIRequestContext,
  orderId: number
) {
  const response = await request.get(`${apiURL}/api/refund-lab/orders/${orderId}`, {
    headers: {
        Authorization: "Bearer demo-token-1-customer",
        "Content-Type": "application/json"
    },
  });

  return  response;
}