import { expect, test } from "../fixtures/app.fixture";

import {
  seedOrder,
  refundOrder,
  checkRefund,
  getLedger,
  openReturnsPage,
  generateIdempotencyKey,
} from "../src/api/refund.api";

test.describe('Week 5 Refund Labs', () => {

    test('Refund Lab', async ({ request, page, log, evidence }) => {

        log.info("Seeding order");
        const order = await seedOrder(request);
        evidence.order = order;

        const orderId = order.id;

        log.info("Opening Returns page", { orderId });
        await openReturnsPage(page, orderId);

        await expect(page.getByTestId('refund-line')).toContainText('₹333.00');
        await expect(page.getByTestId('refunded-TEE')).toHaveText('0');

        log.info("Submitting partial refund");
        const refundResponse = await refundOrder(request, orderId, 1, generateIdempotencyKey());

        expect(refundResponse.ok()).toBeTruthy();

        const refund = await refundResponse.json();
        evidence.refund = refund;

        // Verify refund amount split
        expect(refund.amountPaise).toBe(34965);
        expect(refund.lineAmountPaise).toBe(33300);
        expect(refund.taxPaise).toBe(1665);

        expect(refund.taxShares.reduce((sum: number, tax: number) => sum + tax, 0)).toBe(4995);

        const ledger = await getLedger(request, orderId);
        evidence.ledger = ledger;

        log.info("Ledger verified", {
            status: ledger.status,
            refundCount: ledger.refundCount
        });

        // Refresh UI to get the latest refund details
        await page.reload();

        await expect(page.getByTestId('refunded-TEE')).toHaveText('1');

        expect(ledger.status).toBe('PARTIALLY_REFUNDED');
        expect(ledger.refundCount).toBe(1);
        expect(ledger.refundableBalancePaise).toBe(69930);
        expect(ledger.lastRefund.amountPaise).toBe(34965);

    });

    test('Full Refund', async ({ request, page, log, evidence }) => {

        log.info("Creating order");
        const order = await seedOrder(request);
        evidence.order = order;

        const orderId = order.id;

        log.info("Refunding entire order");
        const refundResponse = await refundOrder(request, orderId, 3, generateIdempotencyKey("full"));

        expect(refundResponse.ok()).toBeTruthy();

        const refund = await refundResponse.json();
        evidence.refund = refund;

        expect(refund.amountPaise).toBe(104895);

        const ledger = await getLedger(request, orderId);
        evidence.ledger = ledger;

        log.info("Full refund completed", {
            status: ledger.status
        });

        // Entire order should now be refunded
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

    // Run the same test with different inputs
    for (const tc of testCases) {

        test(tc.name, async ({ request, log, evidence }) => {

            log.info("Creating order");
            const order = await seedOrder(request);
            evidence.order = order;

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

            expect(result.verdict).toBe(tc.expectedVerdict);

        });

    }

    test('Refuse the two money leaks', async ({ request, log, evidence }) => {

        log.info("Creating order");
        const order = await seedOrder(request);
        evidence.order = order;

        const orderId = order.id;

        const before = await getLedger(request, orderId);
        evidence.ledgerBefore = before;

        log.info("Attempting over refund");

        const overRefund = await refundOrder(request, orderId, 4, 'over-refund');

        expect(overRefund.status()).toBe(422);

        const error = await overRefund.json();
        evidence.overRefundError = error;

        log.info("Over refund rejected", {
            reason: error.reason
        });

        expect(error.message).toBe('Refund rejected');
        expect(error.reason).toBe('OVER_REFUND');
        expect(error.verdict).toBe('OVER_REFUND');

        const after = await getLedger(request, orderId);

        // Balance should remain unchanged after a failed refund
        expect(after.refundableBalancePaise).toBe(before.refundableBalancePaise);

        const idempotencyKey = generateIdempotencyKey();

        const refund1 = await refundOrder(request, orderId, 1, idempotencyKey);

        expect(refund1.ok()).toBeTruthy();

        const refund = await refund1.json();
        evidence.refund = refund;

        // Retry with the same idempotency key
        const refund2 = await refundOrder(request, orderId, 1, idempotencyKey);

        expect(refund2.status()).toBe(200);

        const replay = await refund2.json();
        evidence.replayRefund = replay;

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