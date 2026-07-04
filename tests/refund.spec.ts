
import { expect, test } from "../fixtures/artifact-tests";
 
const posApiUrl = process.env.POS_API_URL || "http://localhost:4000";
 
const authHeaders = {
  Authorization: "Bearer demo-token-1-customer"
};
 
test("Verify a complete refund updates the order status and refundable balance", async ({ request ,evidence}) => {
  const orderResponse = await request.post(
    `${posApiUrl}/api/refund-lab/orders`,
    {
      headers: authHeaders
    }
  );
 
  expect(orderResponse.status()).toBe(201); 
  const order = await orderResponse.json(); 
  evidence.cartResponse = await order;
  const refundResponse = await request.post(
    `${posApiUrl}/api/refunds`,
    {
      headers: {
        ...authHeaders,
        "Idempotency-Key": `refund-${Date.now()}`,
        "Content-Type": "application/json"
      },
      data: {
        orderId: order.id,
        lines: [
          {
            sku: "TEE",
            qty: 3
          }
        ]
      }
    }
  );
 
  expect(refundResponse.status()).toBe(201); 
  const updatedOrderResponse = await request.get(
    `${posApiUrl}/api/refund-lab/orders/${order.id}`,
    {
      headers: authHeaders
    }
  );
 
  expect(updatedOrderResponse.status()).toBe(200); 
  const updatedOrder = await updatedOrderResponse.json(); 
  evidence.cartResponse = await updatedOrder;
  expect(updatedOrder.status).toBe("REFUNDED");
  expect(updatedOrder.refundableBalancePaise).toBe(0);
});
 
test("Partial refund amount and tax distribution are calculated correctly", async ({ request ,evidence}) => {
  const orderResponse = await request.post(
    `${posApiUrl}/api/refund-lab/orders`,
    {
      headers: authHeaders
    }
  );
 
  expect(orderResponse.status()).toBe(201);
  const order = await orderResponse.json();
  evidence.cartResponse = await order;
  const refundResponse = await request.post(
    `${posApiUrl}/api/refunds`,
    {
      headers: {
        ...authHeaders,
        "Idempotency-Key": `refund-${Date.now()}`,
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
 
  expect(refundResponse.status()).toBe(201); 
  const refund = await refundResponse.json(); 
  evidence.cartResponse = await refund;
  expect(refund.amountPaise).toBe(34965);
  expect(refund.lineAmountPaise).toBe(33300);
  expect(refund.taxPaise).toBe(1665);
 
  const totalTax = refund.taxShares.reduce(
    (sum: number, value: number) => sum + value,
    0
  );
 
  expect(totalTax).toBe(4995);
});
 
const cases = [
  {
    name: "Milestone 3 - Approved refund",
    qty: 1,
    verdict: "APPROVED"
  },
  {
    name: "Milestone 3 - Over refund",
    qty: 4,
    verdict: "OVER_REFUND"
  }
];
 
for (const tc of cases) {
  test(tc.name, async ({ request ,evidence}) => {
    const orderResponse = await request.post(
      `${posApiUrl}/api/refund-lab/orders`,
      {
        headers: authHeaders
      }
    );
 
    expect(orderResponse.status()).toBe(201);
 
    const order = await orderResponse.json();
 
    const response = await request.post(
      `${posApiUrl}/api/refunds/check`,
      {
        headers: {
          ...authHeaders,
          "Content-Type": "application/json"
        },
        data: {
          orderId: order.id,
          lines: [
            {
              sku: "TEE",
              qty: tc.qty
            }
          ]
        }
      }
    );
 
    expect(response.status()).toBe(200); 
    const body = await response.json(); 
    evidence.cartResponse = await body;
    expect(body.verdict).toBe(tc.verdict);
    evidence.cartResponse = await response.json();
  });
}
 
test("Verify over refund request is rejected", async ({ request ,evidence}) => {
  const orderResponse = await request.post(
    `${posApiUrl}/api/refund-lab/orders`,
    {
      headers: authHeaders
    }
  );
 
  expect(orderResponse.status()).toBe(201); 
  const order = await orderResponse.json();
 
  const response = await request.post(
    `${posApiUrl}/api/refunds`,
    {
      headers: {
        ...authHeaders,
        "Idempotency-Key": `refund-${Date.now()}`,
        "Content-Type": "application/json"
      },
      data: {
        orderId: order.id,
        lines: [
          {
            sku: "TEE",
            qty: 4
          }
        ]
      }
    }
  );
 
  expect(response.status()).toBe(422);
  const body = await response.json(); 
  evidence.cartResponse = await body;
  expect(body.verdict).toBe("OVER_REFUND");

});
 
test("the same idempotency key creates only one refund", async ({ request,evidence }) => {
  const orderResponse = await request.post(
    `${posApiUrl}/api/refund-lab/orders`,
    {
      headers: authHeaders
    }
  );
 
  expect(orderResponse.status()).toBe(201);
 
  const order = await orderResponse.json();
  evidence.cartResponse = await order;
 
  const key = `refund-${Date.now()}`;
 
  const firstRefund = await request.post(
    `${posApiUrl}/api/refunds`,
    {
      headers: {
        ...authHeaders,
        "Idempotency-Key": key,
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
 
  expect(firstRefund.status()).toBe(201);
 
  const secondRefund = await request.post(
    `${posApiUrl}/api/refunds`,
    {
      headers: {
        ...authHeaders,
        "Idempotency-Key": key,
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
 
  expect(secondRefund.status()).toBe(200);
 
  const orderDetails = await request.get(
    `${posApiUrl}/api/refund-lab/orders/${order.id}`,
    {
      headers: authHeaders
    }
  );
 
  expect(orderDetails.status()).toBe(200);
 
  const finalOrder = await orderDetails.json();
 //the expected refundCount is 1 changed 2
 //after tracing i got to know the error and i changed to the refund count to the 1 so it passed.
  expect(finalOrder.refundCount).toBe(1);

  evidence.cartResponse = await finalOrder;
});