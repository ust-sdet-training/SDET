import { test, expect } from "../fixtures/test";

const apiBaseUrl = process.env.POS_API_URL || "http://localhost:4000";
const authHeaders = {
  Authorization: "Bearer demo-token-1-customer"
};

test.describe("Week 5 refunds stabilization", () => {
  test("refund flow uses paise-safe math, rejects over-refund, and is idempotent", async ({
    evidence,
    page,
    request,
    log
  }) => {
    log.info("Starting refund stabilization test");

    const refundSession = `gate-refund-${Date.now()}`;
    const headers = {
      ...authHeaders,
      "Content-Type": "application/json",
      "X-Refund-Session": refundSession
    };

    const orderResponse = await request.post(`${apiBaseUrl}/api/refund-lab/orders`, {
      headers,
      data: {
        taxPaise: 4995,
        lines: [{ sku: "TEE", name: "Training Tee", unitPaise: 33300, qty: 3 }]
      }
    });
    expect(orderResponse.status()).toBe(201);
    const order = await orderResponse.json();
    await evidence.attachJson("refund-order-created.json", order);

    await page.route("**/api/refund-lab/orders/*", async (route) => {
      await route.continue({
        headers: {
          ...route.request().headers(),
          "X-Refund-Session": refundSession
        }
      });
    });

    await page.goto(`/returns?orderId=${order.id}`);
    await expect(page.getByRole("heading", { name: "Returns & Refunds" })).toBeVisible();
    await expect(page.getByTestId("refund-status")).toHaveText("PAID");
    await expect(page.getByTestId("refund-balance-paise")).toHaveText("104895");
    await expect(page.getByTestId("refunded-TEE")).toHaveText("0");

    const approvedCheck = await request.post(`${apiBaseUrl}/api/refunds/check`, {
      headers,
      data: { orderId: order.id, lines: [{ sku: "TEE", qty: 1 }] }
    });
    expect(approvedCheck.status()).toBe(200);
    await evidence.attachJson("approved-refund-check.json", await approvedCheck.json());

    const overRefundCheck = await request.post(`${apiBaseUrl}/api/refunds/check`, {
      headers,
      data: { orderId: order.id, lines: [{ sku: "TEE", qty: 4 }] }
    });
    expect(overRefundCheck.status()).toBe(200);
    const overRefundCheckBody = await overRefundCheck.json();
    await evidence.attachJson("over-refund-check.json", overRefundCheckBody);
    expect(overRefundCheckBody.verdict).toBe("OVER_REFUND");

    const idempotencyKey = `refund-${Date.now()}`;
    const firstRefund = await request.post(`${apiBaseUrl}/api/refunds`, {
      headers: { ...headers, "Idempotency-Key": idempotencyKey },
      data: { orderId: order.id, lines: [{ sku: "TEE", qty: 1 }] }
    });
    expect(firstRefund.status()).toBe(201);
    const firstRefundBody = await firstRefund.json();
    await evidence.attachJson("first-refund.json", firstRefundBody);

    expect(firstRefundBody.lineAmountPaise).toBe(33300);
    expect(firstRefundBody.taxPaise).toBe(1665);
    expect(firstRefundBody.amountPaise).toBe(34965);
    expect(firstRefundBody.taxShares.reduce((sum: number, tax: number) => sum + tax, 0)).toBe(4995);

    const replayedRefund = await request.post(`${apiBaseUrl}/api/refunds`, {
      headers: { ...headers, "Idempotency-Key": idempotencyKey },
      data: { orderId: order.id, lines: [{ sku: "TEE", qty: 1 }] }
    });
    expect(replayedRefund.status()).toBe(200);
    const replayedRefundBody = await replayedRefund.json();
    await evidence.attachJson("replayed-refund.json", replayedRefundBody);
    expect(replayedRefundBody.replayed).toBe(true);
    expect(replayedRefundBody.refundId).toBe(firstRefundBody.refundId);

    const rejectedRefund = await request.post(`${apiBaseUrl}/api/refunds`, {
      headers: { ...headers, "Idempotency-Key": `refund-over-${Date.now()}` },
      data: { orderId: order.id, lines: [{ sku: "TEE", qty: 4 }] }
    });
    expect(rejectedRefund.status()).toBe(422);
    const rejectedRefundBody = await rejectedRefund.json();
    await evidence.attachJson("rejected-over-refund.json", rejectedRefundBody);
    expect(rejectedRefundBody.verdict).toBe("OVER_REFUND");

    const finalOrderResponse = await request.get(`${apiBaseUrl}/api/refund-lab/orders/${order.id}`, {
      headers
    });
    expect(finalOrderResponse.status()).toBe(200);
    const finalOrder = await finalOrderResponse.json();
    await evidence.attachJson("final-refund-ledger.json", finalOrder);

    expect(finalOrder.status).toBe("PARTIALLY_REFUNDED");
    expect(finalOrder.refundCount).toBe(1);
    expect(finalOrder.refundableBalancePaise).toBe(69930);
    expect(finalOrder.lines.find((line: { sku: string }) => line.sku === "TEE").refundedQty).toBe(1);

    await page.reload();
    await expect(page.getByTestId("refund-status")).toHaveText("PARTIALLY_REFUNDED");
    await expect(page.getByTestId("refund-count")).toHaveText("1");
    await expect(page.getByTestId("refund-balance-paise")).toHaveText("69930");
    await expect(page.getByTestId("refund-total-paise")).toHaveText("34965 paise");
    await expect(page.getByTestId("refunded-TEE")).toHaveText("1");
  });
});
