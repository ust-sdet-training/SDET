import { test, expect } from "../src/fixtures/evidence-fixture";
import crypto from "node:crypto";

const api_url="http://localhost:4000";
const auth_headers = {
    Authorization: "Bearer demo-token-1-customer",
    "Content-Type": "application/json",
};

async function seedOrder(request: any, data: any) {
    const response = await request.post(
        `${api_url}/api/refund-lab/orders`,
        {
            headers: auth_headers,
            data,
        }
    );

    expect(response.status()).toBe(201);
    return await response.json();
}


async function refund(request: any, orderId: number, lines: any, idempotencyKey?: string) {
    const headers = {
        ...auth_headers,
        ...(idempotencyKey ? { "Idempotency-Key": idempotencyKey } : {}),
    };

    return request.post( `${api_url}/api/refunds`,
        {
            headers,
            data: { orderId,
                lines,
            },
        }
    );
}

async function checkEligibility( request: any, orderId: number,lines: any
) {
    const response = await request.post( `${api_url}/api/refunds/check`,
        {
            headers: auth_headers,
            data: {
                orderId,
                lines,
            },
        }
    );
    expect(response.status()).toBe(200);
    return await response.json();
}

async function orderLedger(request: any,orderId: number) {
    const response = await request.get(`${api_url}/api/refund-lab/orders/${orderId}`,
        {
            headers: auth_headers,
        }
    );
    expect(response.status()).toBe(200);
    const order = await response.json();
    const refundedAmountPaise = order.refunds.reduce( (sum: number, refund: any) =>sum + refund.amountPaise,0 );
    return {
        refundCount: order.refundCount,
        refundedAmountPaise,
        refundableBalancePaise:order.totalPaise - refundedAmountPaise,
        status: order.status,
        refunds: order.refunds,
    };
}

async function attachEvidence(testInfo: any,name: string,body: unknown) {
    await testInfo.attach(name, {
        body: JSON.stringify(body, null, 2),
        contentType: "application/json",
    });

}

function formatMoney(paise: number) {
    return new Intl.NumberFormat("en-IN", { style: "currency",currency: "INR", minimumFractionDigits: 2, }).format(paise / 100);
}

// Tests the full refund path
test("Full refund", async ({request,evidence,}) => {

    const order = await seedOrder(request, {
        taxPaise: 180000,
        lines: [
            {
                sku: "SHOE101",
                name: "Running Shoes",
                unitPaise: 450000,
                qty: 4
            }
        ]
    });

    evidence["seed-order"] = order;

    const response = await refund(request,order.id,"ALL",crypto.randomUUID() );

    expect(response.status()).toBe(201);

    const body = await response.json();

    evidence["full-refund"] = body;

    expect(body.orderStatus).toBe("REFUNDED");
    expect(body.refundCount).toBe(1)
    expect(body.amountPaise).toBe(order.totalPaise)

    expect(body.lineAmountPaise).toBe(order.lineTotalPaise);
    expect(body.taxPaise).toBe(order.taxPaise);
    expect(body.refundableBalancePaise).toBe(0)

    const ledger = await orderLedger(request, order.id);
    evidence["ledger"] = ledger;
    expect(ledger.status).toBe("REFUNDED")
    expect(ledger.refundCount).toBe(1);

    expect(ledger.refundableBalancePaise).toBe(0);
    expect(ledger.refundedAmountPaise).toBe(order.totalPaise);
});


//test the partial refund path

test("Partial refund -1 of 4 shoes", async ({request,evidence,}) => {

    const order = await seedOrder(request, {
        taxPaise: 180000,
        lines: [
            {
                sku: "SHOE101",
                name: "Running Shoes",
                unitPaise: 450000,
                qty: 4
            }
        ]
    });

    evidence["seed-order"] = order;

    const response = await refund(request,order.id,
        [
            {
                sku: "SHOE101",
                qty: 1
            }
        ],
        crypto.randomUUID()
    );

    expect(response.status()).toBe(201);

    const body = await response.json();

    evidence["partial-refund"] = body;

    expect(body.orderStatus).toBe("PARTIALLY_REFUNDED");

    expect(body.refundCount).toBe(1);
    expect(body.refundableBalancePaise).toBe(order.totalPaise - body.amountPaise);

    expect(body.amountPaise).toBeGreaterThan(0);
    expect(body.lineAmountPaise).toBe(450000);
    expect(body.taxPaise).toBe(45000);

    const ledger = await orderLedger(request, order.id);

    evidence["ledger"] = ledger;

    expect(ledger.status).toBe("PARTIALLY_REFUNDED");
    expect(ledger.refundCount).toBe(1);
    expect(ledger.refundableBalancePaise).toBe(order.totalPaise - body.amountPaise);
});

//testing multiple partial refunds

test("Multiple partial refunds on same order", async ({request,evidence,}) => {

    const order = await seedOrder(request, {
        taxPaise: 180000,
        lines: [
            {
                sku: "SHOE101",
                name: "Running Shoes",
                unitPaise: 450000,
                qty: 4
            }
        ]
    });

    evidence["seed-order"] = order;

    // Refund 1
    const key1 = crypto.randomUUID();

    const refund1 = await refund(request,order.id,
        [
            {
                sku: "SHOE101",
                qty: 1
            }
        ],
        key1
    );

    expect(refund1.status()).toBe(201);
    const body1 = await refund1.json();
    evidence["refund-1"] = body1;

    expect(body1.orderStatus).toBe("PARTIALLY_REFUNDED");
    expect(body1.refundCount).toBe(1);
    let ledger = await orderLedger(request, order.id);

    expect(ledger.refundCount).toBe(1);
    expect(ledger.status).toBe("PARTIALLY_REFUNDED");

    // Refund 2
    const key2 = crypto.randomUUID();

    const refund2 = await refund(request,order.id,
        [
            {
                sku: "SHOE101",
                qty: 2
            }
        ],
        key2
    );

    expect(refund2.status()).toBe(201);
    const body2 = await refund2.json();
    evidence["refund-2"] = body2;

    expect(body2.orderStatus).toBe("PARTIALLY_REFUNDED");
    expect(body2.refundCount).toBe(2);

    ledger = await orderLedger(request, order.id);

    expect(ledger.refundCount).toBe(2);
    expect(ledger.status).toBe("PARTIALLY_REFUNDED");


    // Refund 3
    const key3 = crypto.randomUUID();
    const refund3 = await refund( request, order.id,
        [
            {
                sku: "SHOE101",
                qty: 1
            }
        ],
        key3
    );

    expect(refund3.status()).toBe(201);
    const body3 = await refund3.json();
    evidence["refund-3"] = body3;

    expect(body3.orderStatus).toBe("REFUNDED");
    expect(body3.refundCount).toBe(3);
    expect(body3.refundableBalancePaise).toBe(0);

    ledger = await orderLedger(request, order.id);

    evidence["final-ledger"] = ledger;
    expect(ledger.status).toBe("REFUNDED");
    expect(ledger.refundCount).toBe(3);
    expect(ledger.refundableBalancePaise).toBe(0);
    expect(ledger.refundedAmountPaise).toBe(order.totalPaise);
});

// Idempotency key checking

test("Partial refund idempotent", async ({request,evidence,}) => {

    const order = await seedOrder(request, {
        taxPaise: 180000,
        lines: [
            {
                sku: "SHOE101",
                name: "Running Shoes",
                unitPaise: 450000,
                qty: 4
            }
        ]
    });

    evidence["seed-order"] = order;
    const key = crypto.randomUUID();
    // First refund

    const first = await refund(request,order.id,
        [
            {
                sku: "SHOE101",
                qty: 1
            }
        ],
        key
    );

    expect(first.status()).toBe(201);
    const firstBody = await first.json();
    evidence["first-refund"] = firstBody;

    expect(firstBody.orderStatus).toBe("PARTIALLY_REFUNDED");
    expect(firstBody.refundCount).toBe(1);

    // same request
    const second = await refund(request,order.id,
        [
            {
                sku: "SHOE101",
                qty: 1
            }
        ],
        key
    );

    expect(second.status()).toBe(200);

    const secondBody = await second.json();

    evidence["replayed-refund"] = secondBody;

    expect(secondBody.replayed).toBe(true);

    // Ledger
  
    const ledger = await orderLedger(request, order.id);
    evidence["ledger"] = ledger;

    expect(ledger.refundCount).toBe(1);
    expect(ledger.status).toBe("PARTIALLY_REFUNDED");

    expect(ledger.refundedAmountPaise).toBe(firstBody.amountPaise);
});

// Tests that refunding more items than purchased is rejected

test("Over refund rejected", async ({ request, evidence }) => {

    const order = await seedOrder(request, {
        taxPaise: 180000,
        lines: [
            {
                sku: "SHOE101",
                name: "Running Shoes",
                unitPaise: 450000,
                qty: 4
            }
        ]
    });

    evidence["seed-order"] = order;

    const beforeLedger = await orderLedger(request, order.id);
    evidence["ledger-before"] = beforeLedger;

    const response = await refund(request,order.id,
        [
            {
                sku: "SHOE101",
                qty: 5
            }
        ],
        crypto.randomUUID()
    );

    expect(response.status()).toBe(422);

    const body = await response.json();
    evidence["over-refund"] = body;

    expect(body.reason).toBe("OVER_REFUND");
    expect(body.verdict).toBe("OVER_REFUND");

    const afterLedger = await orderLedger(request, order.id);
    evidence["ledger-after"] = afterLedger;

    expect(afterLedger.refundCount).toBe(beforeLedger.refundCount);
    expect(afterLedger.refundableBalancePaise).toBe(beforeLedger.refundableBalancePaise);
    expect(afterLedger.refundedAmountPaise).toBe(beforeLedger.refundedAmountPaise);
});


