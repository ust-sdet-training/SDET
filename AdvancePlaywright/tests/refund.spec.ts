import { test, expect } from "../fixtures/evidence.fixture";
import {
    seedOrder,
    refundOrder,
    checkRefund,
    getLedger,
    openReturnsPage,
    generateIdempotencyKey
} from "../src/refund.api";

test.describe("Refund Management", () => {

    test("Verify partial refund", async ({ request, page, evidence, log }) => {

        // Create a new order for testing
        const order = await seedOrder(request);
        evidence.order = order;

        const orderId = order.id;

        // Open the Returns page
        await openReturnsPage(page, orderId);

        // Verify refund amount before refund
        await expect(page.getByTestId("refunded-TEE")).toHaveText("0");

        // Perform a partial refund (1 quantity)
        const refundResponse = await refundOrder(
            request,
            orderId,
            1,
            generateIdempotencyKey()
        );

        expect(refundResponse.ok()).toBeTruthy();

        const refund = await refundResponse.json();
        evidence.refund = refund;

        // Verify refund amount
        expect(refund.amountPaise).toBe(34965);

        // Fetch latest order details
        const ledger = await getLedger(request, orderId);
        evidence.ledger = ledger;

        // Verify order status after refund
        expect(ledger.status).toBe("PARTIALLY_REFUNDED");
        expect(ledger.refundCount).toBe(1);

        log.info("Partial refund completed successfully");

    });
    test("Verify full refund", async ({ request, page, evidence, log }) => {

    // Step 1: Create a new order
    log.info("Creating a new order");

    const order = await seedOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Step 2: Refund all the items in the order
    log.info("Processing full refund");

    const refundResponse = await refundOrder(
        request,
        orderId,
        3, // Refund all 3 items
        generateIdempotencyKey()
    );

    expect(refundResponse.ok()).toBeTruthy();

    const refund = await refundResponse.json();
    evidence.refund = refund;

    // Verify total refund amount
    expect(refund.amountPaise).toBe(104895);

    // Step 3: Get the latest order details
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;

    // Verify order is fully refunded
    expect(ledger.status).toBe("REFUNDED");
    expect(ledger.refundCount).toBe(1);
    expect(ledger.refundableBalancePaise).toBe(0);

    log.info(`Refund Amount : ${refund.amountPaise}`);
    log.info(`Refund Count : ${ledger.refundCount}`);
    log.info(`Order Status : ${ledger.status}`);

    // Step 4: Open Returns page and verify UI
    await openReturnsPage(page, orderId);

    await expect(page.getByTestId("refunded-TEE")).toHaveText("3");
    await expect(page.getByTestId("refund-total")).toHaveText("₹1,048.95");

    log.info("Full refund completed successfully");

});
test("Verify valid refund eligibility", async ({ request, evidence, log }) => {

    // Step 1: Create a new order
    const order = await seedOrder(request);
    evidence.order = order;

    log.info("Checking refund eligibility");

    // Step 2: Check whether refund is allowed
    const result = await checkRefund(
        request,
        order.id,
        "TEE",
        1
    );

    evidence.refundCheck = result;

    // Step 3: Verify refund is approved
    expect(result.verdict).toBe("APPROVED");

    log.info("Refund eligibility verified successfully");

});

test("Verify over refund is rejected", async ({ request, evidence, log }) => {

    // Step 1: Create a new order
    const order = await seedOrder(request);
    evidence.order = order;

    log.info("Checking over refund");

    // Step 2: Try to refund more items than available
    const result = await checkRefund(
        request,
        order.id,
        "TEE",
        4
    );

    evidence.refundCheck = result;

    // Step 3: Verify system rejects the request
    expect(result.verdict).toBe("OVER_REFUND");

    log.info("Over refund rejected successfully");

});

test("Verify idempotent refund", async ({ request, log, evidence }) => {

    // Step 1 : Create Order
    const order = await seedOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Step 2 : Generate one Idempotency Key
    const key = generateIdempotencyKey();

    // Step 3 : First Refund
    const refund1 = await refundOrder(
        request,
        orderId,
        1,
        key
    );

    expect(refund1.ok()).toBeTruthy();

    const refundBody1 = await refund1.json();

    // Step 4 : Send same request again with same key
    const refund2 = await refundOrder(
        request,
        orderId,
        1,
        key
    );

    expect(refund2.ok()).toBeTruthy();

    const refundBody2 = await refund2.json();

    // Step 5 : Verify both refund IDs are same
    expect(refundBody2.refundId).toBe(refundBody1.refundId);

    // Step 6 : Verify only one refund exists
    const ledger = await getLedger(request, orderId);

    expect(ledger.refundCount).toBe(1);

    log.info("Idempotency verified successfully");

});

test("Verify different Idempotency Key creates another refund", async ({ request, log }) => {

    const order = await seedOrder(request);

    const orderId = order.id;

    // First refund
    await refundOrder(
        request,
        orderId,
        1,
        generateIdempotencyKey()
    );

    // Second refund with different key
    await refundOrder(
        request,
        orderId,
        1,
        generateIdempotencyKey()
    );

    const ledger = await getLedger(request, orderId);

    expect(ledger.refundCount).toBe(2);

    log.info("Different Idempotency Key created second refund");

});

});