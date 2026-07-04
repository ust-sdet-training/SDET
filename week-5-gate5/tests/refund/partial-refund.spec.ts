import { test, expect } from "../../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  getLedger,
  openReturnsPage,
  generateIdempotencyKey,
} from "../../src/refund.api";

test.describe("Partial Refund", () => {
  test("Refund Lab", async ({ request, page, log, evidence }) => {
    log.info("Seeding order");
    // Create a fresh order for the test
    const order = await seedOrder(request);
    evidence.order = order;
    const orderId = order.id;
    log.info("Opening Returns page", { orderId });
    await openReturnsPage(page, orderId);
    await expect(page.getByTestId("refund-line")).toContainText("₹333.00");
    await expect(page.getByTestId("refunded-TEE")).toHaveText("0");
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
    // Refund amount for a single item including tax
    expect(refund.amountPaise).toBe(34965);
    expect(refund.lineAmountPaise).toBe(33300);
    expect(refund.taxPaise).toBe(1665);
    // Tax shares should add up to the original tax amount
    expect(refund.taxShares.reduce((sum: number, tax: number) => sum + tax, 0)).toBe(4995);
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;
    log.info("Ledger verified", {
      status: ledger.status,
      refundCount: ledger.refundCount,
    });
    // Reload to reflect the latest refund state
    await page.reload();
    await expect(page.getByTestId("refunded-TEE")).toHaveText("1");
    expect(ledger.status).toBe("PARTIALLY_REFUNDED");
    expect(ledger.refundCount).toBe(1);
    // Remaining refundable amount after one refund
    expect(ledger.refundableBalancePaise).toBe(69930);
    expect(ledger.lastRefund.amountPaise).toBe(34965);
  });
});