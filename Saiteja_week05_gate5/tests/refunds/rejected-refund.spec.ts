import { test, expect } from "../../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  getLedger,
} from "../../src/api/refund.api";

test.describe("Rejected Refund", () => {

  test("should reject an over-refund request", async ({
    request,
    log,
    evidence,
  }) => {

    // Create a fresh order for the rejection scenario.
    log.info("Creating order");
    const order = await seedOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Capture the ledger before attempting an invalid refund.
    const before = await getLedger(request, orderId);
    evidence.ledgerBefore = before;

    log.info("Attempting over refund");

    // Attempt to refund more items than available.
    const overRefund = await refundOrder(
      request,
      orderId,
      4,
      "over-refund"
    );

    // Verify the API rejects the request.
    expect(overRefund.status()).toBe(422);

    const error = await overRefund.json();
    evidence.overRefundError = error;

    log.info("Over refund rejected", {
      reason: error.reason,
    });

    // Validate the rejection response.
    expect(error.message).toBe("Refund rejected");
    expect(error.reason).toBe("OVER_REFUND");
    expect(error.verdict).toBe("OVER_REFUND");

    // Verify the ledger remains unchanged after rejection.
    const after = await getLedger(request, orderId);
    evidence.ledgerAfter = after;

    expect(after.refundableBalancePaise).toBe(
      before.refundableBalancePaise
    );

    log.info("Verified ledger remains unchanged after rejection", {
      refundableBalancePaise: after.refundableBalancePaise,
    });

  });

});