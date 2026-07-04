import { test, expect } from "../fixtures/app";

import {
    seedOrder,
    refundOrder,
    checkRefund,
    getLedger,
    openReturnsPage,
    generateIdempotencyKey,
} from "../utils/api";

const testCases = [
    {
        name: 'Valid refund',
        sku: 'CAP',
        qty: 3,
        expectedVerdict: 'APPROVED'
    },
    {
        name: 'Partial refund',
        sku: 'CAP',
        qty: 1,
        expectedVerdict: 'APPROVED'
    },
    {
        name: 'Over refund',
        sku: 'CAP',
        qty: 4,
        expectedVerdict: 'OVER_REFUND'
    }
];

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

test("Demonstration of idempotency key to fail 2 refund", async ({ request }) => {
    const order = await seedOrder(request);
    // Added idempoteny key to make sure no money is leaked during refund when trying for double refund
    const key = generateIdempotencyKey();

    const first = await refundOrder(request, order.id, 3, key);
    const replay = await refundOrder(request, order.id, 3, key);

    expect(first.status()).toBe(201);
    expect(replay.status()).toBe(200);

    const final = await getLedger(request, order.id)

    expect(final.refundCount).toBe(1);
});