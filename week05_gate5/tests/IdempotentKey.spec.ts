import { test, expect } from "../fixtures/app.fixture";
const authHeaders = 
{
  Authorization: "Bearer demo-token-1-customer"
};
const posApiUrl = "http://localhost:4000";
test("Check for the uniqueness of idempotent key", async ({ request,log,evidence }) => {
  const orderResponse = await request.post(
    `${posApiUrl}/api/refund-lab/orders`,
    {
      headers: authHeaders
    }
  );
 
  expect(orderResponse.status()).toBe(201);
 
  const order = await orderResponse.json();
 
   const key = `refund-${Date.now()}`;
 
  const firstRefund = await request.post(
    `${posApiUrl}/api/refunds`,
    {
      headers: {
        ...authHeaders,
           "Idempotency-Key":key,
        "Content-Type": "application/json"
      },
      data: {
        orderId: order.id,
        lines: [
          {
            sku: "TEE",
            qty: 1
          }
        ]
      }
    }
     );
      log.info("Original idempotent key")
  expect(firstRefund.status()).toBe(201);
     evidence.OrigialKey=firstRefund
  const secondRefund = await request.post(
    `${posApiUrl}/api/refunds`,
    {
      headers: {
        ...authHeaders,
         "Idempotency-Key": "other key",
        "Content-Type": "application/json"
      },
      data: {
        orderId: order.id,
        lines: [
          {
            sku: "TEE",
            qty: 1
          }
        ]
      }
    }
  );
   log.info("dupicate idempotent key")
    evidence.DuplicateKey=secondRefund
  expect(secondRefund.status()).toBe(200);
 
  const orderDetails = await request.get(
    `${posApiUrl}/api/refund-lab/orders/${order.id}`,
    {
      headers: authHeaders
    }
  );
       

  expect(orderDetails.status()).toBe(200);
 
  const finalOrder = await orderDetails.json();
  evidence.refundlog=finalOrder
  expect(finalOrder.refundCount).toBe(1);
});
 