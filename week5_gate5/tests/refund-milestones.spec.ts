import { expect, test } from "../fixtures/artifact-test";
import {
  seedOrder,
  refundOrder,
  checkRefund,
  getLedger,
  generateIdempotencyKey
} from "../src/api/refund.api";

test("Milestone 1 - Verify a complete refund updates the order status and refundable balance", async ({ request }) => {
  const order = await seedOrder(request);

  const refundResponse = await refundOrder(
    request,
    order.id,
    3,
    generateIdempotencyKey()
  );

  expect(refundResponse.status()).toBe(201);

  const updatedOrder = await getLedger(request, order.id);

  // Entire order should now be refunded
  expect(updatedOrder.status).toBe("REFUNDED");
  expect(updatedOrder.refundableBalancePaise).toBe(0);
});

test("Milestone 2 - Verify partial refund amount and tax distribution are calculated correctly", async ({ request }) => {
  const order = await seedOrder(request);

  const refundResponse = await refundOrder(
    request,
    order.id,
    1,
    generateIdempotencyKey()
  );

  expect(refundResponse.status()).toBe(201);

  const refund = await refundResponse.json();

  // Verify refund amount and tax split
  expect(refund.amountPaise).toBe(34965);
  expect(refund.lineAmountPaise).toBe(33300);
  expect(refund.taxPaise).toBe(1665);

  expect(
    refund.taxShares.reduce((sum: number, value: number) => sum + value, 0)
  ).toBe(4995);
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

// Execute the same test with different inputs
for (const tc of cases) {
  test(tc.name, async ({ request }) => {
    const order = await seedOrder(request);

    const result = await checkRefund(
      request,
      order.id,
      "TEE",
      tc.qty
    );

    expect(result.verdict).toBe(tc.verdict);
  });
}

test("Milestone 4 - Verify over refund request is rejected", async ({ request }) => {
  const order = await seedOrder(request);

  const response = await refundOrder(
    request,
    order.id,
    4,
    generateIdempotencyKey()
  );

  expect(response.status()).toBe(422);

  const body = await response.json();

  expect(body.verdict).toBe("OVER_REFUND");
});

test("Milestone 5 - Verify the same idempotency key creates only one refund", async ({ request }) => {
  const order = await seedOrder(request);

  const key = generateIdempotencyKey();

  const firstRefund = await refundOrder(
    request,
    order.id,
    1,
    key
  );

  expect(firstRefund.status()).toBe(201);

  // Replay the request using the same idempotency key
  const secondRefund = await refundOrder(
    request,
    order.id,
    1,
    key
  );

  expect(secondRefund.status()).toBe(200);

  const finalOrder = await getLedger(request, order.id);

  // Only one refund should be recorded
  expect(finalOrder.refundCount).toBe(1);
});