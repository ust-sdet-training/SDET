import { APIRequestContext, expect } from "@playwright/test";

export async function refundOrder(
  request: APIRequestContext,
  id:number,
  idempotentKey:string,
  qty:number,
  sku:string
) {
  const response = await request.post("http://localhost:4000/api/refunds", {
    headers: {
        Authorization: "Bearer demo-token-1-customer",
        "Idempotency-Key": idempotentKey,
        "Content-Type": "application/json"
    },
    data: {
        orderId: id,
          "lines": [
            {
                "sku": sku,
                "qty": qty,
            }
        ]
    },
  });
  return response;
  
}