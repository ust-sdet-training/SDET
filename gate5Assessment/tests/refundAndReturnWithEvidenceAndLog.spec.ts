import { test, expect } from "../fixtures/evidence";
import {
    createOrder,
    refundOrder,
    checkRefund,
    getLedger,
    openReturnsPage,
    generateIdempotencyKey,
} from "../src/api/refund.api";

test.describe('Gate 5 — Returns & Refund Testing', () => {

  test('Should process a complete refund and verify refund status, ledger and UI', async ({ request, page, log, evidence }) => {

    // make a new order to test with
    log.info("Creating a new order for full refund validation");
    const testOrder = await createOrder(request);

    // keep this for the report
    evidence.order = testOrder;

    const oId = testOrder.id;

    // refunding all 3 units on the order
    log.info("Submitting full refund request");
    const rawRefundResp = await refundOrder(
        request,
        oId,
        3,
        generateIdempotencyKey("full-refund")
    );

    // should be a 200-ish response, not an error
    expect(rawRefundResp.ok()).toBeTruthy();

    // need the body before we can check anything in it
    const refundJson = await rawRefundResp.json();
    evidence.refund = refundJson;

    // pulling the ledger fresh so we see the real backend state
    const ledgerNow = await getLedger(request, oId);
    evidence.ledger = ledgerNow;

    log.info("Refund completed successfully", {
        refundStatus: ledgerNow.status,
    });

    // status should flip to REFUNDED
    expect(ledgerNow.status).toBe("REFUNDED");
    // nothing left to refund
    expect(ledgerNow.refundableBalancePaise).toBe(0);
    // only one refund should exist
    expect(ledgerNow.refundCount).toBe(1);
    // ledger and API response should agree on the amount
    expect(ledgerNow.lastRefund.amountPaise).toBe(refundJson.amountPaise);

    // now go check the actual page
    await openReturnsPage(page, oId);

    // paise to rupees, then format the way the UI shows it
    await expect(page.getByTestId("refund-total")).toHaveText(
        `₹${(refundJson.amountPaise / 100).toLocaleString("en-IN", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
        })}`
    );

    log.info("Verified full refund in backend ledger and Returns page");
});

 test('should verify prorates tax and balances shares', async ({ request, page, log, evidence }) => {
        log.info("create order for tax");
        const order = await createOrder(request);
        evidence.order = order;

        const orderId = order.id;

        log.info("Opening Returns page", { orderId });
        await openReturnsPage(page, orderId);
        await expect(page.getByTestId('refunded-TEE')).toHaveText('0');
        
        log.info("Submitting partial refund");
        const refundResponse = await refundOrder(request, orderId, 1, generateIdempotencyKey());
        expect(refundResponse.ok()).toBeTruthy();

        const refund = await refundResponse.json();
        evidence.refund = refund;

        // Balancing invariant: tax shares must sum to the ORDER's total tax (4995),
        // not to themselves — this is the actual check that catches a money leak.
        const taxSum = refund.taxShares.reduce((sum: number, tax: number) => sum + tax, 0);
        expect(taxSum).toBe(order.taxPaise);
        log.info("Verified balancing invariant on tax shares", {
            taxShares: refund.taxShares,
            orderTaxPaise: order.taxPaise,
        });

        const ledger = await getLedger(request, orderId);
        evidence.ledger = ledger;

        await page.reload();
        await expect(page.getByTestId('refunded-TEE')).toHaveText('1');

        await expect(page.getByTestId('refund-total')).toHaveText(
            `₹${(refund.amountPaise / 100).toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`
        );
        log.info("Verified UI shows prorated refund total");

        expect(ledger.status).toBe('PARTIALLY_REFUNDED');
        expect(ledger.refundCount).toBe(1);
        log.info("Verified partial refund prorated correctly", {
            lineAmountPaise: refund.lineAmountPaise,
            taxPaise: refund.taxPaise,
            remainingBalance: ledger.refundableBalancePaise,
        });
    });
test.describe('Refund Eligibility Validation - Data Driven Tests', () => {

    // list of scenarios we want to check - one test gets generated per row
    const ruleSet = [

        {
            name: 'Should approve refund for a valid return request',
            seedOptions: {},
            sku: 'TEE',
            qty: 2,
            expectedVerdict: 'APPROVED',
        },

        {
            name: 'Should reject refund when requested quantity exceeds purchased quantity',
            seedOptions: {},
            sku: 'TEE',
            qty: 5,
            expectedVerdict: 'OVER_REFUND',
        },

        {
            name: 'Should reject refund after the return window has expired',
            seedOptions: { purchaseDaysAgo: 60 },
            sku: 'TEE',
            qty: 1,
            expectedVerdict: 'OUT_OF_WINDOW',
        },

        {
            name: 'Should reject refund for final sale items',
            seedOptions: { finalSale: true },
            sku: 'TEE',
            qty: 2,
            expectedVerdict: 'FINAL_SALE',
        },

        {
            name: 'Should reject refund for non-returnable products',
            seedOptions: { returnable: false },
            sku: 'TEE',
            qty: 2,
            expectedVerdict: 'NON_RETURNABLE',
        },
    ];

    // loop through the list above, one test per entry
    for (const row of ruleSet) {

        test(row.name, async ({ request, log, evidence }) => {

            // set up the order however this scenario needs it
            log.info("Creating order for eligibility validation", {
                scenario: row.name,
            });

            const rowOrder = await createOrder(request, row.seedOptions);

            evidence.order = rowOrder;

            // just checking, not actually refunding yet
            log.info("Checking refund eligibility");

            const verdictObj = await checkRefund(
                request,
                rowOrder.id,
                row.sku,
                row.qty
            );

            evidence.refundCheck = verdictObj;

            // just want the logs to make sense when someone reads them later
            if (verdictObj.verdict !== "APPROVED") {

                log.warn("Refund request rejected by business rule", {
                    expected: row.expectedVerdict,
                    actual: verdictObj.verdict,
                });

            } else {

                log.info("Refund request approved");

            }

            // this is the actual check we care about
            expect(verdictObj.verdict).toBe(row.expectedVerdict);

            log.info("Eligibility validation completed successfully");

        });

    }

});

test('Should verify refund behavior with and without idempotency key', async ({
    request,
    log,
    evidence,
}) => {


    // first part - no key at all this time
    log.info("Creating order to validate refund requests without idempotency key");

    const oA = await createOrder(request);
    evidence.orderWithoutKey = oA;

    const oAId = oA.id;

    log.info("Submitting first refund request");

    const respA1 = await refundOrder(
        request,
        oAId,
        1
    );

    expect(respA1.ok()).toBeTruthy();

    const jsonA1 = await respA1.json();

    // same call again, still no key
    log.info("Submitting second refund request without idempotency key");

    const respA2 = await refundOrder(
        request,
        oAId,
        1
    );

    expect(respA2.ok()).toBeTruthy();

    const jsonA2 = await respA2.json();

    // these two ids should be different since nothing tied them together
    expect(jsonA1.refundId).not.toBe(jsonA2.refundId);

    log.warn("Duplicate refund requests created separate refund records because no idempotency key was provided");

    const ledgerA = await getLedger(request, oAId);
    evidence.ledgerWithoutKey = ledgerA;

    // two separate refunds should show up here
    expect(ledgerA.refundCount).toBe(2);

    log.info("Verified two refund records were created");

    // just in case something's off, log it clearly
    if (ledgerA.refundCount !== 2) {
        log.error("Unexpected refund count for requests without idempotency key", {
            refundCount: ledgerA.refundCount,
        });
    }

    // second part - same key sent twice on purpose this time
    log.info("Creating order to validate idempotency");

    const oB = await createOrder(request);
    evidence.orderWithKey = oB;

    const oBId = oB.id;

    // one key, used for both calls below
    const keyValue = generateIdempotencyKey();

    log.info("Submitting first refund request with idempotency key");

    const respB1 = await refundOrder(
        request,
        oBId,
        1,
        keyValue
    );

    expect(respB1.ok()).toBeTruthy();

    const jsonB1 = await respB1.json();

    // sending the exact same thing again with the same key
    log.info("Submitting duplicate refund request with the same idempotency key");

    const respB2 = await refundOrder(
        request,
        oBId,
        1,
        keyValue
    );

    expect(respB2.ok()).toBeTruthy();

    const jsonB2 = await respB2.json();

    // should be the same refund both times, not two different ones
    expect(jsonB2.refundId).toBe(jsonB1.refundId);
    expect(jsonB2.amountPaise).toBe(jsonB1.amountPaise);

    log.warn("Duplicate refund request was replayed instead of creating another refund");

    const ledgerB = await getLedger(request, oBId);
    evidence.ledgerWithKey = ledgerB;

    // still just a partial refund on this one
    expect(ledgerB.status).toBe("PARTIALLY_REFUNDED");
    // and only one refund, even though we sent the request twice
    expect(ledgerB.refundCount).toBe(1);

    log.info("Verified only one refund exists after duplicate request");

    if (ledgerB.refundCount !== 1) {
        log.error("Idempotency validation failed", {
            refundCount: ledgerB.refundCount,
        });
    }

});


  test('Should collect complete evidence for a successful refund transaction', async ({
    request,
    page,
    log,
    evidence,
}, testInfo) => {

    log.info("Creating a new order");

    const evOrder = await createOrder(request);
    evidence.order = evOrder;

    const evOrderId = evOrder.id;

    // snapshot before we touch anything
    log.info("Capturing ledger before refund");

    const snapBefore = await getLedger(request, evOrderId);
    evidence.ledgerBefore = snapBefore;

    log.warn("Ledger snapshot captured before refund");

    log.info("Submitting refund request");

    const evResp = await refundOrder(
        request,
        evOrderId,
        1,
        generateIdempotencyKey("evidence")
    );

    expect(evResp.ok()).toBeTruthy();

    const evRefundJson = await evResp.json();
    evidence.refund = evRefundJson;

    log.info("Refund completed successfully");

    // and snapshot again after, so we can compare
    log.info("Capturing ledger after refund");

    const snapAfter = await getLedger(request, evOrderId);
    evidence.ledgerAfter = snapAfter;

    log.warn("Ledger updated after refund transaction");

    log.info("Opening Returns page");

    await openReturnsPage(page, evOrderId);

    // checking what's actually shown on the page
    await expect(page.getByTestId("refunded-TEE")).toHaveText("1");

    log.info("Verified refunded item count on UI");

    // putting everything together so it's easy to review later
    const packToAttach = {
        order: evidence.order,
        refund: evidence.refund,
        ledgerBefore: evidence.ledgerBefore,
        ledgerAfter: evidence.ledgerAfter,
    };

    // just double checking nothing's missing before we attach it
    if (
        !evidence.order ||
        !evidence.refund ||
        !evidence.ledgerBefore ||
        !evidence.ledgerAfter
    ) {
        log.warn("Evidence package is incomplete");
    }

    // this shows up in the html report
    await testInfo.attach("refund-evidence-pack.json", {
        body: JSON.stringify(packToAttach, null, 2),
        contentType: "application/json",
    });

    log.info("Evidence JSON attached");

    // grabbing a screenshot too, for proof
    const shotPath = testInfo.outputPath("refund-final-state.png");

    await page.screenshot({
        path: shotPath,
        fullPage: true,
    });

    await testInfo.attach("refund-final-state.png", {
        path: shotPath,
        contentType: "image/png",
    });

    log.info("Final screenshot attached");

    log.warn("Evidence collection completed. Review attachments before closing the test.");
});

});