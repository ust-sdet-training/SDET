import { test, expect } from "../fixtures/app.fixture";
const posApiUrl = "http://localhost:4000";
 
test("cart total of by paise", async ({ request, log ,evidence}) =>
{
    
  const orderResponse = await request.post(
    `${posApiUrl}/api/refund-lab/orders`,
    {
      headers: 
      {
        Authorization: "Bearer demo-token-1-customer"
      }
      ,
      data:
            {
                "taxPaise": 4995,
                "lines": 
                [
                    {
                    "sku": "TEE",
                    "name": "Training Tee",
                    "unitPaise": 33300,
                    "qty": 3
                    }
                ]
            }
        });
    log.info
  expect(orderResponse.status()).toBe(201);
  console.log(orderResponse)
  const order = await orderResponse.json();
  log.info("view order details")
  evidence.order=order;
  const refundResponse = await request.post(
    `${posApiUrl}/api/refunds`,
    {
      headers: {
        Authorization: "Bearer demo-token-1-customer",
        "Idempotency-Key": `refund-${Date.now()}`,
        "Content-Type": "application/json"
      },
      data: 
      {
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
 console.log(refundResponse)
  const refund = await refundResponse.json();
  expect(refund.amountPaise).toBe(refund.lineAmountPaise+refund.taxPaise);
  expect(refund.lineAmountPaise).toBe(33300);
  expect(refund.taxPaise).toBe(1665);
  const taxTotal = refund.taxShares.reduce((sum: number, value: number) => sum + value,0);
  expect(taxTotal).toBe(4995);//here i increases the the decimal point to make the test fail 
});