import { test, expect } from "../../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  getLedger,
  generateIdempotencyKey,
} from "../../src/refund.api";

test.describe("Refund Security", () => {
  test("Refuse the two money leaks", async ({
    request,
    log,
    evidence,
  }) => {
    log.info("Creating order");
    const order = await seedOrder(request);
    evidence.order = order;
    const orderId = order.id;
    const before = await getLedger(request, orderId);
    evidence.ledgerBefore = before;
    log.info("Attempting over refund");
    // Try to refund more items than were purchased
    const overRefund = await refundOrder(
      request,
      orderId,
      4,
      "over-refund"
    );
    expect(overRefund.status()).toBe(422);
    const error = await overRefund.json();
    evidence.overRefundError = error;
    log.info("Over refund rejected", {
      reason: error.reason,
    });
    expect(error.message).toBe("Refund rejected");
    expect(error.reason).toBe("OVER_REFUND");
    expect(error.verdict).toBe("OVER_REFUND");
    // Failed refund should not change the balance
    const after = await getLedger(request, orderId);
    expect(after.refundableBalancePaise).toBe(
      before.refundableBalancePaise
    );
    const idempotencyKey = generateIdempotencyKey();
    const refund1 = await refundOrder(
      request,
      orderId,
      1,
      idempotencyKey
    );
    expect(refund1.ok()).toBeTruthy();
    const refund = await refund1.json();
    evidence.refund = refund;
    // Replay the same refund request with the same idempotency key
    const refund2 = await refundOrder(
      request,
      orderId,
      1,
      idempotencyKey
    );
    expect(refund2.status()).toBe(200);
    const replay = await refund2.json();
    evidence.replayRefund = replay;
    expect(replay.refundId).toBe(refund.refundId);
    expect(replay.amountPaise).toBe(refund.amountPaise);
    const ledger = await getLedger(request, orderId);
    evidence.ledgerAfter = ledger;
    log.info("Verified idempotent refund", {
      refundCount: ledger.refundCount,
      balance: ledger.refundableBalancePaise,
    });
    // Only one refund should be recorded
    expect(ledger.status).toBe("PARTIALLY_REFUNDED");
    expect(ledger.refundCount).toBe(1);
    // Remaining balance after a single refund
    expect(ledger.refundableBalancePaise).toBe(69930);
    expect(ledger.lastRefund.amountPaise).toBe(34965);
  });
});