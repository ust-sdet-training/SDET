import { expect, test } from '../fixtures/evidence';

test("Post a refund twice without idempotency key", async ({ request, evidence }) => {
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

    const firstRefundResponse = await request.post("http://localhost:4000/api/refunds", {
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

    await firstRefundResponse.json();

    const secondRefundResponse = await request.post("http://localhost:4000/api/refunds", {
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

    await secondRefundResponse.json();

    const finalLedgerResponse = await request.get(`http://localhost:4000/api/refund-lab/orders/${order.id}`, {
        headers: {
            Authorization: "Bearer demo-token-1-customer"
        }
    });

    const finalLedger = await finalLedgerResponse.json();

    expect(finalLedger.refundCount).toBe(1);

    evidence.response = finalLedger;
});