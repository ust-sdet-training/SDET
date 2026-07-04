import { test, expect } from "../../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  getLedger,
  generateIdempotencyKey,
} from "../../src/api/refund.api";

test.describe("Duplicate Refund and Idempotency", () => {

  test("should process only one refund for duplicate requests", async ({
    request,
    log,
    evidence,
  }) => {

    // Create a fresh order for the idempotency scenario.
    log.info("Creating order");
    const order = await seedOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Generate a unique idempotency key.
    const idempotencyKey = generateIdempotencyKey();

    // Submit the first refund request.
    log.info("Submitting first refund request");

    const refund1 = await refundOrder(
      request,
      orderId,
      1,
      idempotencyKey
    );

    expect(refund1.ok()).toBeTruthy();

    const refund = await refund1.json();
    evidence.refund = refund;

    // Replay the same refund request using the same idempotency key.
    log.info("Submitting duplicate refund request");

    const refund2 = await refundOrder(
      request,
      orderId,
      1,
      idempotencyKey
    );

    expect(refund2.status()).toBe(200);

    const replay = await refund2.json();
    evidence.replayRefund = replay;

    // Verify the same refund is returned instead of creating a new one.
    expect(replay.refundId).toBe(refund.refundId);
    expect(replay.amountPaise).toBe(refund.amountPaise);

    // Retrieve the ledger after both requests.
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;

    log.info("Verified idempotent refund", {
      refundCount: ledger.refundCount,
      refundableBalancePaise: ledger.refundableBalancePaise,
    });

    // Validate only one refund was processed.
    expect(ledger.status).toBe("PARTIALLY_REFUNDED");
    expect(ledger.refundCount).toBe(1);
    expect(ledger.refundableBalancePaise).toBe(69930);

    // Validate refund values using paise.
    expect(ledger.lastRefund.amountPaise).toBe(34965);

  });

});