import { expect, test } from "../fixtures/evidence";

const posApiUrl = process.env.POS_API_URL || "http://localhost:4000";

const authHeaders = {
    Authorization: "Bearer demo-token-1-customer",
};

test.describe("refund flow example", () => {

    // simple: create an order and perform a full refund
    test("full refund", async ({ request, evidence }) => {
        const orderResponse = await request.post(`${posApiUrl}/api/refund-lab/orders`, {
            headers: authHeaders,
        });
        expect(orderResponse.status()).toBe(201);

        const order = await orderResponse.json();

        const refundResponse = await request.post(`${posApiUrl}/api/refunds`, {
            headers: {
                ...authHeaders,
                "Idempotency-Key": `refund-full-${Date.now()}`,
                "Content-Type": "application/json",
            },
            data: {
                orderId: order.id,
                lines: [{ sku: "TEE", qty: 3 }],
            },
        });
        expect(refundResponse.status()).toBe(201);

        const refund = await refundResponse.json();

        const orderDetails = await request.get(`${posApiUrl}/api/refund-lab/orders/${order.id}`, {
            headers: authHeaders,
        });
        expect(orderDetails.status()).toBe(200);

        const updatedOrder = await orderDetails.json();

        evidence.cartResponse = { order, refund, updatedOrder };
        evidence.diagnosis = "Full refund: order is refunded and refundable balance is 0 paise.";

        expect(updatedOrder.status).toBe("REFUNDED");
        expect(updatedOrder.refundableBalancePaise).toBe(0);
    });

    // simple: perform a partial refund and check paise values
    test("partial refund in paise", async ({ request, evidence }) => {
        const orderResponse = await request.post(`${posApiUrl}/api/refund-lab/orders`, {
            headers: authHeaders,
        });
        expect(orderResponse.status()).toBe(201);

        const order = await orderResponse.json();

        const refundResponse = await request.post(`${posApiUrl}/api/refunds`, {
            headers: {
                ...authHeaders,
                "Idempotency-Key": `refund-partial-${Date.now()}`,
                "Content-Type": "application/json",
            },
            data: {
                orderId: order.id,
                lines: [{ sku: "TEE", qty: 1 }],
            },
        });
        expect(refundResponse.status()).toBe(201);

        const refund = await refundResponse.json();
        const totalTax = refund.taxShares.reduce((sum: number, value: number) => sum + value, 0);

        evidence.cartResponse = { order, refund, totalTax };
        evidence.diagnosis = "Partial refund: amount and tax are checked exactly in paise.";

        expect(refund.amountPaise).toBe(34965);
        expect(refund.lineAmountPaise).toBe(33300);
        expect(refund.taxPaise).toBe(1665);
        expect(totalTax).toBe(4995);
    });

    // simple: attempt an over-refund and expect rejection
    test("rejected refund", async ({ request, evidence }) => {
        const orderResponse = await request.post(`${posApiUrl}/api/refund-lab/orders`, {
            headers: authHeaders,
        });
        expect(orderResponse.status()).toBe(201);

        const order = await orderResponse.json();

        const refundResponse = await request.post(`${posApiUrl}/api/refunds`, {
            headers: {
                ...authHeaders,
                "Idempotency-Key": `refund-reject-${Date.now()}`,
                "Content-Type": "application/json",
            },
            data: {
                orderId: order.id,
                lines: [{ sku: "TEE", qty: 4 }],
            },
        });
        expect(refundResponse.status()).toBe(422);

        const body = await refundResponse.json();

        evidence.cartResponse = { order, body };
        evidence.diagnosis = "Rejected refund: over-refund is blocked.";

        expect(body.verdict).toBe("OVER_REFUND");
    });

    // simple: send same refund twice and check idempotency
    test("idempotent refund", async ({ request, evidence }) => {
        const orderResponse = await request.post(`${posApiUrl}/api/refund-lab/orders`, {
            headers: authHeaders,
        });
        expect(orderResponse.status()).toBe(201);

        const order = await orderResponse.json();
        const key = `refund-idempotent-${Date.now()}`;

        const firstRefund = await request.post(`${posApiUrl}/api/refunds`, {
            headers: {
                ...authHeaders,
                "Idempotency-Key": key,
                "Content-Type": "application/json",
            },
            data: {
                orderId: order.id,
                lines: [{ sku: "TEE", qty: 1 }],
            },
        });
        expect(firstRefund.status()).toBe(201);

        const secondRefund = await request.post(`${posApiUrl}/api/refunds`, {
            headers: {
                ...authHeaders,
                "Idempotency-Key": key,
                "Content-Type": "application/json",
            },
            data: {
                orderId: order.id,
                lines: [{ sku: "TEE", qty: 1 }],
            },
        });
        expect(secondRefund.status()).toBe(200);

        const orderDetails = await request.get(`${posApiUrl}/api/refund-lab/orders/${order.id}`, {
            headers: authHeaders,
        });
        expect(orderDetails.status()).toBe(200);

        const finalOrder = await orderDetails.json();

        evidence.cartResponse = {
            order,
            firstRefund: await firstRefund.json(),
            secondRefund: await secondRefund.json(),
            finalOrder,
        };
        evidence.diagnosis = "Idempotent refund: same key returns same refund and refundCount stays 1.";

        expect(finalOrder.refundCount).toBe(1);
    });

});
