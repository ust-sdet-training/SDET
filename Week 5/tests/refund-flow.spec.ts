import { test, expect } from "../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  checkRefund,
  getLedger,
  openReturnsPage,
  generateIdempotencyKey,
} from "../src/refund.api";

const testCases = [
  {
    name: "Full valid refund",
    sku: "TEE",
    qty: 3,
    expectedVerdict: "APPROVED",
  },
  {
    name: "Partial refund",
    sku: "TEE",
    qty: 1,
    expectedVerdict: "APPROVED",
  },
  {
    name: "Rejected over refund",
    sku: "TEE",
    qty: 4,
    expectedVerdict: "OVER_REFUND",
  },
  {
    name: "Rejected already refunded order",
    sku: "TEE",
    qty: 1,
    expectedVerdict: "ALREADY_REFUNDED",
    refunded: true,
  },
];

// Run the same test for each refund scenario
for (const tc of testCases) {
  test(tc.name, async ({ request, log, evidence }) => {

    // Create a new order
    const seed = await seedOrder(request);
    evidence.order = seed;
    // Generate a unique idempotency key
    const key = crypto.randomUUID();
    // Fully refund the order if required
    if (tc.refunded) {
      await refundOrder(
        request,
        seed.id,
        3,
        key
      );
    }
    // Check the refund eligibility
    const result = await checkRefund(
      request,
      seed.id,
      tc.sku,
      tc.qty
    );
    // Save the refund check result
    evidence.refundCheck = result;
    // Verify the expected refund verdict
    expect(result.verdict).toBe(tc.expectedVerdict);
  });
}

test("No double pay, Idempotent", async ({ request, log, evidence }) => {

  // Create a new order
  const order = await seedOrder(request);
  evidence.order = order;
  const orderId = order.id;
  // Get the ledger before refund
  const before = await getLedger(request, orderId);
  evidence.ledgerBefore = before;
  // Generate an idempotency key
  const idempotencyKey = generateIdempotencyKey();
  // Send the first refund request
  const refund1 = await refundOrder(request, orderId, 1, idempotencyKey);
  expect(refund1.ok()).toBeTruthy();
  const refund = await refund1.json();
  evidence.refund = refund;

  // Send the same refund request again
  const refund2 = await refundOrder(request, orderId, 1, idempotencyKey);
  expect(refund2.status()).toBe(200);
  const replay = await refund2.json();
  evidence.replayRefund = replay;

  // Verify both responses are identical
  expect(replay.refundId).toBe(refund.refundId);
  expect(replay.amountPaise).toBe(refund.amountPaise);

  // Get the ledger after refund
  const ledger = await getLedger(request, orderId);
  evidence.ledgerAfter = ledger;

  // Verify only one refund was processed
  expect(ledger.status).toBe("PARTIALLY_REFUNDED");
  expect(ledger.refundCount).toBe(1);
  expect(ledger.refundableBalancePaise).toBe(69930);
  expect(ledger.lastRefund.amountPaise).toBe(34965);
});