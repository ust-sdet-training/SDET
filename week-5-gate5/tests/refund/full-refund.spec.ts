import { test, expect } from "../../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  getLedger,
  openReturnsPage,
  generateIdempotencyKey,
} from "../../src/refund.api";

test.describe("Full Refund", () => {
  test("Full Refund", async ({ request, page, log, evidence }) => {
    log.info("Creating order");
    // Create a fresh order for the test
    const order = await seedOrder(request);
    evidence.order = order;
    const orderId = order.id;
    log.info("Refunding entire order");
    const refundResponse = await refundOrder(
      request,
      orderId,
      3,
      generateIdempotencyKey("full")
    );
    expect(refundResponse.ok()).toBeTruthy();
    const refund = await refundResponse.json();
    evidence.refund = refund;
    // Full order amount including tax
    expect(refund.amountPaise).toBe(104895);
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;
    log.info("Full refund completed", {
      status: ledger.status,
    });
    expect(ledger.status).toBe("REFUNDED");
    expect(ledger.refundableBalancePaise).toBe(0);
    expect(ledger.refundCount).toBe(1);
    // Validate refund breakdown
    expect(refund.lineAmountPaise).toBe(99900);
    expect(refund.taxPaise).toBe(4995);
    expect(ledger.lastRefund.amountPaise).toBe(104895);
    // Verify refund details in the UI
    await openReturnsPage(page, orderId);
    await expect(page.getByTestId("refunded-TEE")).toHaveText("3");
    await expect(page.getByTestId("refund-total")).toHaveText("₹1,048.95");
  });
});