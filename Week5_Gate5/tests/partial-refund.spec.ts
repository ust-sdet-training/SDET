import { test, expect } from '../fixtures/evidence';

test("Handle a partial refund", async ({ page, request, evidence }) => {
    const orderResponse = await request.post("http://localhost:4000/api/refund-lab/orders", {
        headers: {
            Authorization: "Bearer demo-token-1-customer",
            "Content-Type": "application/json"
        },
        data: {
            taxPaise: 4995,
            lines: [{
                sku: "TEE",
                name: "Training Tee",
                unitPaise: 33300,
                qty: 3
            }]
        }
    });

    const order = await orderResponse.json();

    const checkResponse = await request.post("http://localhost:4000/api/refunds/check", {
        headers: {
            Authorization: "Bearer demo-token-1-customer",
            "Content-Type": "application/json"
        },
        data: {
            orderId: order.id,
            lines: [{
                sku: "TEE",
                qty: 1
            }]
        }
    });

    const result = await checkResponse.json();

    expect(result.verdict).toBe("APPROVED");

    const refundResponse = await request.post("http://localhost:4000/api/refunds", {
        headers: {
        Authorization: "Bearer demo-token-1-customer",
            "Idempotency-Key": crypto.randomUUID(),
            "Content-Type": "application/json"
        },
        data: {
            orderId: order.id,
            lines: [{
                sku: "TEE",
                qty: 1
            }]
        }
    });

    const refund = await refundResponse.json();

    expect(refund.amountPaise).toBe(34965);
    expect(refund.taxShares.reduce((a: number, x: number) => a + x, 0)).toBe(4995);

    await page.goto(`/returns?orderId=${order.id}`);

    await expect(page.getByTestId("refund-total")).toHaveText("₹349.65");

    await expect(page.getByTestId("refund-status")).toHaveText("PARTIALLY_REFUNDED");

    evidence.response = refund;
});