import { test, expect } from "../../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  getLedger,
  openReturnsPage,
  generateIdempotencyKey,
} from "../../src/api/refund.api";

test.describe("Full Refund", () => {

  test("should successfully process a full refund", async ({
    request,
    page,
    log,
    evidence,
  }) => {

    // Create a fresh order for the full refund scenario.
    log.info("Creating order");
    const order = await seedOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Refund the complete order.
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

    // Validate the total refunded amount in paise.
    expect(refund.amountPaise).toBe(104895);

    // Retrieve the ledger after the refund.
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;

    log.info("Full refund completed", {
      status: ledger.status,
    });

    // Validate ledger details.
    expect(ledger.status).toBe("REFUNDED");
    expect(ledger.refundableBalancePaise).toBe(0);
    expect(ledger.refundCount).toBe(1);
    expect(refund.lineAmountPaise).toBe(99900);
    expect(refund.taxPaise).toBe(4995);
    expect(ledger.lastRefund.amountPaise).toBe(104895);

    // Open the Returns page to verify the UI.
    await openReturnsPage(page, orderId);

    // Confirm all items have been refunded.
    await expect(page.getByTestId("refunded-TEE")).toHaveText("3");

    // Verify the total refund displayed to the user.
    await expect(page.getByTestId("refund-total")).toHaveText("₹1,048.95");
  });

});