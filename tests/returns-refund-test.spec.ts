import { test, expect } from "../fixtures/evidence";

const BASE_URL = "http://localhost:4000/api";
const TOKEN = "Bearer demo-token-1-customer";

// creating order function so that I can use this function whenever I want to create a order.
async function createOrder(req: any) {
    const res = await req.post(`${BASE_URL}/refund-lab/orders`, {
        headers: {
            Authorization: TOKEN
        },
        data: {
            taxPaise: 5752,
            lines: [
                {
                    sku: "JOG",
                    name: "Joggers Men",
                    unitPaise: 79900,
                    qty: 4
                }
            ]
        }
    })
    expect(res.ok()).toBeTruthy()
    const body = await res.json()
    return body
}

// Testing for the full flow and checking valid amount is refunded, asserting for over refund
// asserting the refund with same idempotency key and asserting ledger before and after refund.
test("A partial refund testing", async ({request, page, log, evidence}) => {
    log.info("Creating order")
    // Creating the order and validating the order response
    const order = await createOrder(request)
    log.info("order created", order)
    await evidence("order created", order)

    expect(order.status).toBe("PAID")
    expect(order.totalPaise).toBe(325352)
    log.info("order validated")

    // Making a refund request and checking for approval and asserting
    log.info("checking refund approval")
    const check = await request.post(`${BASE_URL}/refunds/check`, {
        headers: {
            Authorization: TOKEN
        },
        data: {
            orderId: order.id,
            lines: [
                {
                    sku: "JOG",
                    qty: 1
                }
            ]
        }
    })

    const checkBody = await check.json()
    await evidence("refund approval", checkBody)
    expect(checkBody.verdict).toBe("APPROVED");
    log.info("refund approval checked", checkBody)

    // Generating a unique key - idempotency key
    const key = crypto.randomUUID()

    // Checking for refund request status and validating the refund amount and tax
    log.info("Checking refund status")
    const refund = await request.post(`${BASE_URL}/refunds`, {
        headers: {
            Authorization: TOKEN,
            "Idempotency-Key": key
        },
        data: {
            orderId: order.id,
            lines: [
                {
                    sku: "JOG",
                    qty: 1
                }
            ]
        }
    })
    const refundBody = await refund.json()
    await evidence("refund status", refundBody)
    log.info("Refund response", refundBody)
    expect(refundBody.amountPaise).toBe(81338)
    expect(refundBody.taxPaise).toBe(1438)
    expect(refundBody.taxShares.reduce((a: number, b: number) => a + b, 0)).toBe(5752)
    log.info("Refund status checked")

    // checking the ledger after refund done
    log.info("getting Ledger")
    const ledger = await request.get(`${BASE_URL}/refund-lab/orders/${order.id}`,
        {
            headers: {
                Authorization: TOKEN
            }
        })
    const ledgerBody = await ledger.json()
    await evidence("ledger", ledgerBody)
    log.info("checking ledger", ledgerBody)
    expect(ledgerBody.status).toBe("PARTIALLY_REFUNDED")
    expect(ledgerBody.refundCount).toBe(1)
    expect(ledgerBody.refundableBalancePaise).toBe(244014)
    log.info("ledger checked")

    // Checking if quantity is more than the order quantity in refund request - rejected
    log.info("Checking for over refund")
    const overRefund = await request.post(`${BASE_URL}/refunds`, {
        headers: {
            Authorization: TOKEN,
            "Idempotency-Key": crypto.randomUUID()
        },
        data: {
            orderId: order.id,
            lines: [
                {
                    sku: "JOG",
                    qty: 5
                }
            ]
        }
    })
    const overRefundBody = await overRefund.json()
    await evidence("No over-refund", overRefundBody)
    log.info("over-refund checking", overRefundBody)
    expect(overRefundBody.reason).toBe("OVER_REFUND")
    log.info("Over-refund checked")

    // making a refund request with same idempotemcy key - should not create new refund request
    log.info("checking refund with same idempotency key")
    const replay = await request.post(`${BASE_URL}/refunds`, {
        headers: {
            Authorization: TOKEN,
            "Idempotency-Key": key
        },
        data: {
            orderId: order.id,
            lines: [
                {
                    sku: "JOG",
                    qty: 1
                }
            ]
        }
    })
    const replayBody = await replay.json()
    await evidence("same idempotency key", replayBody)
    log.info("same idempotency key", replayBody)
    expect(replayBody.refundId).toBe(refundBody.refundId)
    log.info("same refund status replayed")

    // Asserting the UI
    await page.goto(`/returns?orderId=${order.id}`)

    await expect(page.getByTestId("refund-total")).toHaveText("₹813.38")

    await page.screenshot({
        path: `artifacts/refund-${order.id}.png`,
        fullPage: true
    })
})