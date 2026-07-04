import { test, expect } from "../fixtures/app.fixture";

// Import reusable API helper methods
import {
  seedOrder,
  refundOrder,
  checkRefund,
  getLedger,
  openReturnsPage,
  generateIdempotencyKey,
} from "../src/api/refund.api";


test.describe('Create test order and check Return and Refund along with the trace view', () => {

    test('Refund functionality for partial refund', async ({ request, page,log, evidence }) => {

       // Create a test order
        log.info("Seeding order");
        const order = await seedOrder(request);
        evidence.OrderDetail = order;

        const orderId = order.id;

         // Open Returns page for the created order
        log.info("Opening Returns page", { orderId });
        await openReturnsPage(page, orderId);

        await expect(page.getByTestId('refund-line')).toContainText('₹333.00');
        await expect(page.getByTestId('refunded-TEE')).toHaveText('0');

        // Perform a partial refund
        log.info("Submitting partial refund");
        const refundResponse = await refundOrder(request, orderId, 1, generateIdempotencyKey());

        expect(refundResponse.ok()).toBeTruthy();

        const refund = await refundResponse.json();
        evidence.Refund = refund;

        // Validate refund calculation
        expect(refund.amountPaise).toBe(34965);
        expect(refund.lineAmountPaise).toBe(33300);
        expect(refund.taxPaise).toBe(1665);

        expect(refund.taxShares.reduce((sum: number, tax: number) => sum + tax, 0)).toBe(4995);

        // Retrieve and validate refund ledger
        const ledger = await getLedger(request, orderId);
        evidence.Ledger = ledger;

        log.info("Ledger verified", {
            status: ledger.status,
            refundCount: ledger.refundCount
        });

        // Refresh UI and verify refunded quantity
        await page.reload();
        await expect(page.getByTestId('refunded-TEE')).toHaveText('1');

        // Validate ledger details
        expect(ledger.status).toBe('PARTIALLY_REFUNDED');
        expect(ledger.refundCount).toBe(1);
        expect(ledger.refundableBalancePaise).toBe(69930);
        expect(ledger.lastRefund.amountPaise).toBe(34965);

    });

    test('Refund functionality for full refund', async ({ request, page, log, evidence }) => {

        log.info("Creating order");
        const order = await seedOrder(request);
        evidence.OrderDetail = order;

        const orderId = order.id;

        // Refund the complete order
        log.info("Refunding entire order");
        const refundResponse = await refundOrder(request, orderId, 3, generateIdempotencyKey("full"));

        // Validate full refund amount
        expect(refundResponse.ok()).toBeTruthy();

        const refund = await refundResponse.json();
        evidence.Refund = refund;

        expect(refund.amountPaise).toBe(104895);

        const ledger = await getLedger(request, orderId);
        evidence.Ledger = ledger;

        log.info("Full refund completed", {
        status: ledger.status
    });

    // Verify refund details in UI
        expect(ledger.status).toBe('REFUNDED');
        expect(ledger.refundableBalancePaise).toBe(0);
        expect(ledger.refundCount).toBe(1);
        expect(refund.lineAmountPaise).toBe(99900);
        expect(refund.taxPaise).toBe(4995);
        expect(ledger.lastRefund.amountPaise).toBe(104895);

        await openReturnsPage(page, orderId);

        await expect(page.getByTestId('refunded-TEE')).toHaveText('3');

        await expect(page.getByTestId('refund-total')).toHaveText('₹1,048.95');

    });

     // Data-driven refund validation scenarios
  const testCases = [
    {
      name: 'Valid refund',
      sku: 'TEE',
      qty: 1,
      expectedVerdict: 'APPROVED'
    },
    {
      name: 'Over refund',
      sku: 'TEE',
      qty: 4,
      expectedVerdict: 'OVER_REFUND'
    }
  ];

   // Execute refund validation for each test case
  for (const tc of testCases) {

    test(tc.name, async ({ request, log, evidence }) => {

       // Create a fresh order
        log.info("Creating order");
        const order = await seedOrder(request);
        evidence.order = order;

        // Check refund eligibility
        log.info("Checking refund eligibility");
        const result = await checkRefund(
            request,
            order.id,
            tc.sku,
            tc.qty
        );
      evidence.refundCheck = result;
      log.info("Refund verdict", {
            verdict: result.verdict
        });

        // Validate refund decision
      expect(result.verdict).toBe(tc.expectedVerdict);

    });

  }

  test('REjected Refund, Duplicate refund, Idempotency', async ({ request, log, evidence }) => {

    log.info("Creating order");
    const order = await seedOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Capture ledger before refund
    const before = await getLedger(request, orderId);
    evidence.ledgerBefore = before;
     // Attempt an invalid over-refund
    log.info("Attempting over refund");

    const overRefund = await refundOrder(request, orderId, 4, 'over-refund');

    // Rejected Refund verification
    expect(overRefund.status()).toBe(422);

    const error = await overRefund.json();
    evidence.overRefundError = error;
    log.info("Over refund rejected", {
        reason: error.reason
    });

    expect(error.message).toBe('Refund rejected');
    expect(error.reason).toBe('OVER_REFUND');
    expect(error.verdict).toBe('OVER_REFUND');

    // Verify ledger remains unchanged
    const after = await getLedger(request, orderId);

    expect(after.refundableBalancePaise).toBe(before.refundableBalancePaise);

    // Verify idempotent refund handling
    const idempotencyKey = generateIdempotencyKey();

    const refund1 = await refundOrder(request, orderId, 1, idempotencyKey);

    expect(refund1.ok()).toBeTruthy();

    const refund = await refund1.json();
    evidence.refund = refund;

    // Repeat same refund request with same idempotency key
    const refund2 = await refundOrder(request, orderId, 1, idempotencyKey);

    expect(refund2.status()).toBe(200);
    const replay = await refund2.json();
    evidence.replayRefund = replay;

    // Verify duplicate refund was not processed twice
    expect(replay.refundId).toBe(refund.refundId);
    expect(replay.amountPaise).toBe(refund.amountPaise);

    const ledger = await getLedger(request, orderId);
    evidence.ledgerAfter = ledger;

    log.info("Verified idempotent refund", {
        refundCount: ledger.refundCount,
        balance: ledger.refundableBalancePaise
    });

    expect(ledger.status).toBe('PARTIALLY_REFUNDED');
    expect(ledger.refundCount).toBe(1);
    expect(ledger.refundableBalancePaise).toBe(69930);
    expect(ledger.lastRefund.amountPaise).toBe(34965);

  });

});