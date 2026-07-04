import { test, expect } from "../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  checkRefund,
  getLedger,
  openReturnsPage,
  generateIdempotencyKey,
} from "../src/api/refund.api";

test('Duplicate refund request should create only one refund using the same Idempotency-Key', async ({ request, log, evidence }) => {

    // Create a new order
    log.info("Creating order");
    const order = await seedOrder(request);
    evidence.order = order;
    const orderId = order.id;

    // Generate one idempotency key
    const idempotencyKey = generateIdempotencyKey();

    // Submit first refund
    const refund1 = await refundOrder(request,orderId,1,idempotencyKey);

    expect(refund1.ok()).toBeTruthy();

    const firstRefund = await refund1.json();
    evidence.refund = firstRefund;

    // Submit the same refund again with the SAME key
    const refund2 = await refundOrder(request,orderId,1,idempotencyKey);

    expect(refund2.ok()).toBeTruthy();

    const replay = await refund2.json();
    evidence.replayRefund = replay;

    // Verify the same refund is returned
    expect(replay.refundId).toBe(firstRefund.refundId);
    expect(replay.amountPaise).toBe(firstRefund.amountPaise);

    // Verify only one refund exists
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;

    log.info("Verified idempotent refund", {refundCount: ledger.refundCount});
    
    expect(ledger.refundCount).toBe(1);
    expect(ledger.status).toBe("PARTIALLY_REFUNDED");
    expect(ledger.refundableBalancePaise).toBe(69930);

});