import { test, expect } from "../../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  getLedger,
  openReturnsPage,
  generateIdempotencyKey,
} from "../../src/api/refund.api";

test.describe("Partial Refund", () => {

  test("should successfully process a partial refund", async ({
    request,
    page,
    log,
    evidence,
  }) => {

    // Create a fresh order for the partial refund scenario.
    log.info("Seeding order");
    const order = await seedOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Open the returns page for the created order.
    log.info("Opening Returns page", { orderId });
    await openReturnsPage(page, orderId);

    // Verify the initial refund details before processing.
    await expect(page.getByTestId("refund-line")).toContainText("₹333.00");
    await expect(page.getByTestId("refunded-TEE")).toHaveText("0");

    // Submit a partial refund for one item.
    log.info("Submitting partial refund");
    const refundResponse = await refundOrder(
      request,
      orderId,
      1,
      generateIdempotencyKey()
    );

    expect(refundResponse.ok()).toBeTruthy();

    const refund = await refundResponse.json();
    evidence.refund = refund;

    // Validate refund values using paise (minor units).
    expect(refund.amountPaise).toBe(34965);
    expect(refund.lineAmountPaise).toBe(33300);
    expect(refund.taxPaise).toBe(1665);

    // Verify total tax allocation.
    expect(
      refund.taxShares.reduce(
        (sum: number, tax: number) => sum + tax,
        0
      )
    ).toBe(4995);

    // Verify the refund ledger after processing.
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;

    log.info("Ledger verified", {
      status: ledger.status,
      refundCount: ledger.refundCount,
    });

    // Reload the page to confirm UI updates.
    await page.reload();

    // Verify one item has been refunded.
    await expect(page.getByTestId("refunded-TEE")).toHaveText("1");

    // Validate ledger values.
    expect(ledger.status).toBe("PARTIALLY_REFUNDED");
    expect(ledger.refundCount).toBe(1);
    expect(ledger.refundableBalancePaise).toBe(69930);
    expect(ledger.lastRefund.amountPaise).toBe(34965);
  });

});