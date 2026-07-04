import { test, expect } from "../fixtures/evidence";
import { type APIRequestContext } from "@playwright/test";
import { logger } from "../utils/logger";
import { randomUUID } from "crypto";

const BASE_URL = process.env.POS_API_URL ?? "http://localhost:4000";
const SKU = "TEE";
const headers = {
    Authorization: "Bearer demo-token-1-customer",
};

async function createOrder(request: APIRequestContext) {

    // Create a order 
    logger.info("Creating a new order");

    const response = await request.post(`${BASE_URL}/api/refund-lab/orders`, {
        headers,
    });

    expect(response.status()).toBe(201);

    const order = await response.json();

    logger.info({
        message: "Order created",
        orderId: order.id,
        status: order.status,
        refundableBalancePaise: order.refundableBalancePaise,
    });

    return order;
}

async function submitRefund(
    request: APIRequestContext,
    orderId: number,
    quantity: number,
    idempotencyKey = randomUUID()
) {
    // Send the refund request 
    logger.info({
        message: "Sending refund request",
        orderId,
        quantity,
    });

    const response = await request.post(`${BASE_URL}/api/refunds`, {
        headers: {
            ...headers,
            "Content-Type": "application/json",
            "Idempotency-Key": idempotencyKey,
        },
        data: {
            orderId,
            lines: [{ sku: SKU, qty: quantity }],
        },
    });

    logger.info({
        message: "Refund request finished",
        statusCode: response.status(),
    });
    return response;
}

async function getOrder(request: APIRequestContext, orderId: number) {

    // get the latest order state
    logger.info({
        message: "Fetching order",
        orderId,
    });

    const response = await request.get(`${BASE_URL}/api/refund-lab/orders/${orderId}`, {
        headers,
    });

    expect(response.status()).toBe(200);

    const order = await response.json();

    logger.info({
        message: "Order loaded",
        orderId: order.id,
        status: order.status,
        refundCount: order.refundCount,
        refundableBalancePaise: order.refundableBalancePaise,
    });

    return order;
}

test.describe("Refund flow", () => {

    test("refunds the full order", async ({ request, evidence }) => {
        logger.info("Starting full refund test");

        const order = await createOrder(request);
        const refundResponse = await submitRefund(request, order.id, 3);

        expect(refundResponse.status()).toBe(201);

        const refund = await refundResponse.json();
        const updatedOrder = await getOrder(request, order.id);

        expect(updatedOrder.status).toBe("REFUNDED");
        expect(updatedOrder.refundableBalancePaise).toBe(0);

        logger.info({
            message: "Full refund verified",
            orderId: updatedOrder.id,
            refundAmountPaise: refund.amountPaise,
        });

        evidence.order = order;
        evidence.refund = refund;
        evidence.updatedOrder = updatedOrder;
    });

    test("refunds part of the order", async ({ request, evidence }) => {
        logger.info("Starting partial refund test");

        const order = await createOrder(request);
        const refundResponse = await submitRefund(request, order.id, 1);

        expect(refundResponse.status()).toBe(201);

        const refund = await refundResponse.json();

        expect(refund.amountPaise).toBe(34965);
        expect(refund.lineAmountPaise).toBe(33300);
        expect(refund.taxPaise).toBe(1665);

        const totalTax = refund.taxShares.reduce((sum: number, tax: number) => sum + tax, 0);
        expect(totalTax).toBe(4995);

        const updatedOrder = await getOrder(request, order.id);

        expect(updatedOrder.status).toBe("PARTIALLY_REFUNDED");
        expect(updatedOrder.refundCount).toBe(1);
        expect(updatedOrder.refundableBalancePaise).toBe(69930);

        logger.info({
            message: "Partial refund verified",
            orderId: updatedOrder.id,
            refundAmountPaise: refund.amountPaise,
            remainingBalancePaise: updatedOrder.refundableBalancePaise,
        });

        evidence.order = order;
        evidence.refund = refund;
        evidence.updatedOrder = updatedOrder;
    });

    test("rejects a refund when quantity is too high", async ({ request, evidence }) => {
        logger.info("Starting rejected refund test");

        const order = await createOrder(request);
        const refundResponse = await submitRefund(request, order.id, 4);

        expect(refundResponse.status()).toBe(422);

        const rejectedRefund = await refundResponse.json();

        expect(rejectedRefund.verdict).toBe("OVER_REFUND");
        expect(rejectedRefund.reason).toBe("OVER_REFUND");

        logger.warn({
            message: "Refund was rejected",
            orderId: order.id,
            verdict: rejectedRefund.verdict,
            reason: rejectedRefund.reason,
        });

        const updatedOrder = await getOrder(request, order.id);

        expect(updatedOrder.status).toBe("PAID");
        expect(updatedOrder.refundCount).toBe(0);

        evidence.order = order;
        evidence.rejectedRefund = rejectedRefund;
        evidence.updatedOrder = updatedOrder;
    });

    test("reuses the same refund when the idempotency key is repeated", async ({ request, evidence }) => {
        logger.info("Starting idempotent refund test");

        const order = await createOrder(request);
        const idempotencyKey = randomUUID();

        const firstResponse = await submitRefund(request, order.id, 1, idempotencyKey);
        expect(firstResponse.status()).toBe(201);

        const firstRefund = await firstResponse.json();

        const replayResponse = await submitRefund(request, order.id, 1, idempotencyKey);
        expect(replayResponse.status()).toBe(200);
        const replayRefund = await replayResponse.json();

        const updatedOrder = await getOrder(request, order.id);

        expect(updatedOrder.refundCount).toBe(1);

        if ("replayed" in replayRefund) {
            expect(replayRefund.replayed).toBe(true);
        }

        logger.info({
            message: "Idempotent refund verified",
            orderId: updatedOrder.id,
            refundCount: updatedOrder.refundCount,
        });

        evidence.order = order;
        evidence.firstRefund = firstRefund;
        evidence.replayRefund = replayRefund;
        evidence.updatedOrder = updatedOrder;
    });
});