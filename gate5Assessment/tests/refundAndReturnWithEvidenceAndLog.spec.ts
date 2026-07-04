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

    // Create a new order that will be used for refund testing.
    log.info("Creating a new order for full refund validation");
    const order = await createOrder(request);

    // Store the order details as evidence for reporting.
    evidence.order = order;

    const orderId = order.id;

    // Refund all purchased items from the order.
    log.info("Submitting full refund request");
    const refundResponse = await refundOrder(
        request,
        orderId,
        3,
        generateIdempotencyKey("full-refund")
    );

    // Verify that the refund request was successful.
    expect(refundResponse.ok()).toBeTruthy();

    // Save the refund response for evidence.
    const refund = await refundResponse.json();
    evidence.refund = refund;

    // Retrieve the updated ledger after the refund.
    // Ledger stores refund status, remaining balance and refund history.
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;

    log.info("Refund completed successfully", {
        refundStatus: ledger.status,
    });

    // Verify backend refund details.
    expect(ledger.status).toBe("REFUNDED");
    expect(ledger.refundableBalancePaise).toBe(0);
    expect(ledger.refundCount).toBe(1);
    expect(ledger.lastRefund.amountPaise).toBe(refund.amountPaise);

    // Open the Returns page to verify what the user sees.
    await openReturnsPage(page, orderId);

    // Verify the refunded amount displayed on the UI.
    await expect(page.getByTestId("refund-total")).toHaveText(
        `₹${(refund.amountPaise / 100).toLocaleString("en-IN", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
        })}`
    );

    log.info("Verified full refund in backend ledger and Returns page");
});


test.describe('Refund Eligibility Validation - Data Driven Tests', () => {

    // Each object represents one business scenario.
    // The expected verdict is verified against the API response.
    const testCases = [

        {
            name: 'Should approve refund for a valid return request',
            seedOptions: {},
            sku: 'TEE',
            qty: 1,
            expectedVerdict: 'APPROVED',
        },

        {
            name: 'Should reject refund when requested quantity exceeds purchased quantity',
            seedOptions: {},
            sku: 'TEE',
            qty: 4,
            expectedVerdict: 'OVER_REFUND',
        },

        {
            name: 'Should reject refund after the return window has expired',
            seedOptions: { purchaseDaysAgo: 45 },
            sku: 'TEE',
            qty: 1,
            expectedVerdict: 'OUT_OF_WINDOW',
        },

        {
            name: 'Should reject refund for final sale items',
            seedOptions: { finalSale: true },
            sku: 'TEE',
            qty: 1,
            expectedVerdict: 'FINAL_SALE',
        },

        {
            name: 'Should reject refund for non-returnable products',
            seedOptions: { returnable: false },
            sku: 'TEE',
            qty: 1,
            expectedVerdict: 'NON_RETURNABLE',
        },
    ];

    // Run the same validation for every test scenario.
    for (const tc of testCases) {

        test(tc.name, async ({ request, log, evidence }) => {

            // Create a new order based on the current scenario.
            log.info("Creating order for eligibility validation", {
                scenario: tc.name,
            });

            const order = await createOrder(request, tc.seedOptions);

            // Save order details for evidence.
            evidence.order = order;

            // Check whether the refund is allowed.
            log.info("Checking refund eligibility");

            const result = await checkRefund(
                request,
                order.id,
                tc.sku,
                tc.qty
            );

            // Save API response for reporting.
            evidence.refundCheck = result;

            // Expected business rule rejection.
            // These are valid application behaviours,
            // not test failures.
            if (result.verdict !== "APPROVED") {

                log.warn("Refund request rejected by business rule", {
                    expected: tc.expectedVerdict,
                    actual: result.verdict,
                });

            } else {

                log.info("Refund request approved");

            }

            // Verify the returned verdict.
            expect(result.verdict).toBe(tc.expectedVerdict);

            log.info("Eligibility validation completed successfully");

        });

    }

});

test('Should verify refund behavior with and without idempotency key', async ({
    request,
    log,
    evidence,
}) => {


    // Scenario : Refund requests without an idempotency key.
    // Every request is treated as a new request, so two refunds
    // should be created.

    log.info("Creating order to validate refund requests without idempotency key");

    const orderWithoutKey = await createOrder(request);
    evidence.orderWithoutKey = orderWithoutKey;

    const orderWithoutKeyId = orderWithoutKey.id;

    // Submit the first refund request.
    log.info("Submitting first refund request");

    const refundResponse1 = await refundOrder(
        request,
        orderWithoutKeyId,
        1
    );

    expect(refundResponse1.ok()).toBeTruthy();

    const refund1 = await refundResponse1.json();

    // Submit the same refund request again without an idempotency key.
    log.info("Submitting second refund request without idempotency key");

    const refundResponse2 = await refundOrder(
        request,
        orderWithoutKeyId,
        1
    );

    expect(refundResponse2.ok()).toBeTruthy();

    const refund2 = await refundResponse2.json();

    // Verify both requests created different refunds.
    expect(refund1.refundId).not.toBe(refund2.refundId);

    log.warn("Duplicate refund requests created separate refund records because no idempotency key was provided");

    // Capture the ledger after both refunds.
    const ledgerWithoutKey = await getLedger(request, orderWithoutKeyId);
    evidence.ledgerWithoutKey = ledgerWithoutKey;

    expect(ledgerWithoutKey.refundCount).toBe(2);

    log.info("Verified two refund records were created");

    // Extra validation.
    if (ledgerWithoutKey.refundCount !== 2) {
        log.error("Unexpected refund count for requests without idempotency key", {
            refundCount: ledgerWithoutKey.refundCount,
        });
    }

    // Scenario : Refund requests with the same idempotency key.
    // Duplicate requests should return the same refund instead of
    // creating a new one.


    log.info("Creating order to validate idempotency");

    const orderWithKey = await createOrder(request);
    evidence.orderWithKey = orderWithKey;

    const orderWithKeyId = orderWithKey.id;

    // Generate one idempotency key.
    // This key will be reused for both requests.
    const idempotencyKey = generateIdempotencyKey();

    log.info("Submitting first refund request with idempotency key");

    const firstRefundResponse = await refundOrder(
        request,
        orderWithKeyId,
        1,
        idempotencyKey
    );

    expect(firstRefundResponse.ok()).toBeTruthy();

    const firstRefund = await firstRefundResponse.json();

    // Submit the same request again using the same key.
    log.info("Submitting duplicate refund request with the same idempotency key");

    const secondRefundResponse = await refundOrder(
        request,
        orderWithKeyId,
        1,
        idempotencyKey
    );

    expect(secondRefundResponse.ok()).toBeTruthy();

    const replayRefund = await secondRefundResponse.json();

    // Verify the backend replayed the previous refund.
    expect(replayRefund.refundId).toBe(firstRefund.refundId);
    expect(replayRefund.amountPaise).toBe(firstRefund.amountPaise);

    log.warn("Duplicate refund request was replayed instead of creating another refund");

    // Retrieve the final ledger.
    const ledgerWithKey = await getLedger(request, orderWithKeyId);
    evidence.ledgerWithKey = ledgerWithKey;

    expect(ledgerWithKey.status).toBe("PARTIALLY_REFUNDED");
    expect(ledgerWithKey.refundCount).toBe(1);

    log.info("Verified only one refund exists after duplicate request");

    // Extra validation.
    if (ledgerWithKey.refundCount !== 1) {
        log.error("Idempotency validation failed", {
            refundCount: ledgerWithKey.refundCount,
        });
    }

});


  test('Should collect complete evidence for a successful refund transaction', async ({
    request,
    page,
    log,
    evidence,
}, testInfo) => {

    // Create a new order for evidence collection.
    log.info("Creating a new order");

    const order = await createOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Capture ledger before refund.
    log.info("Capturing ledger before refund");

    const ledgerBefore = await getLedger(request, orderId);
    evidence.ledgerBefore = ledgerBefore;

    // Warn because this snapshot will be used for comparison later.
    log.warn("Ledger snapshot captured before refund");

    // Submit refund request.
    log.info("Submitting refund request");

    const refundResponse = await refundOrder(
        request,
        orderId,
        1,
        generateIdempotencyKey("evidence")
    );

    expect(refundResponse.ok()).toBeTruthy();

    // Store refund response.
    const refund = await refundResponse.json();
    evidence.refund = refund;

    log.info("Refund completed successfully");

    // Capture ledger after refund.
    log.info("Capturing ledger after refund");

    const ledgerAfter = await getLedger(request, orderId);
    evidence.ledgerAfter = ledgerAfter;

    // Warn because this ledger will be compared with the previous one.
    log.warn("Ledger updated after refund transaction");

    // Open Returns page.
    log.info("Opening Returns page");

    await openReturnsPage(page, orderId);

    // Verify refunded item count.
    await expect(page.getByTestId("refunded-TEE")).toHaveText("1");

    log.info("Verified refunded item count on UI");

    // Create evidence package.
    const evidencePack = {
        order: evidence.order,
        refund: evidence.refund,
        ledgerBefore: evidence.ledgerBefore,
        ledgerAfter: evidence.ledgerAfter,
    };

    // Warn if any evidence is missing.
    if (
        !evidence.order ||
        !evidence.refund ||
        !evidence.ledgerBefore ||
        !evidence.ledgerAfter
    ) {
        log.warn("Evidence package is incomplete");
    }

    // Attach JSON evidence.
    await testInfo.attach("refund-evidence-pack.json", {
        body: JSON.stringify(evidencePack, null, 2),
        contentType: "application/json",
    });

    log.info("Evidence JSON attached");

    // Capture screenshot of the final UI.
    const screenshotPath = testInfo.outputPath("refund-final-state.png");

    await page.screenshot({
        path: screenshotPath,
        fullPage: true,
    });

    // Attach screenshot.
    await testInfo.attach("refund-final-state.png", {
        path: screenshotPath,
        contentType: "image/png",
    });

    log.info("Final screenshot attached");

    // Final reminder that all evidence has been collected.
    log.warn("Evidence collection completed. Review attachments before closing the test.");
});

});