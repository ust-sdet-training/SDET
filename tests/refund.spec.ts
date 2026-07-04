import { test, expect } from "../fixtures/test";

const API_BASE_URL = process.env.POS_API_URL || "http://localhost:4000";
const AUTH_HEADERS = {
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
      ...AUTH_HEADERS,
      "Content-Type": "application/json",
      "X-Refund-Session": refundSession
    };

    const createOrderResponse = await request.post(`${API_BASE_URL}/api/refund-lab/orders`, {
      headers,
      data: {
        taxPaise: 4995,
        lines: [{ sku: "TEE", name: "Training Tee", unitPaise: 33300, qty: 3 }]
      }
    });
    expect(createOrderResponse.status()).toBe(201);
    const order = await createOrderResponse.json();
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

    const checkRefund = (lines: Array<{ sku: string; qty: number }>) =>
      request.post(`${API_BASE_URL}/api/refunds/check`, {
        headers,
        data: { orderId: order.id, lines }
      });

    const validCheckResponse = await checkRefund([{ sku: "TEE", qty: 1 }]);
    expect(validCheckResponse.status()).toBe(200);
    await evidence.attachJson("approved-refund-check.json", await validCheckResponse.json());

    const excessiveCheckResponse = await checkRefund([{ sku: "TEE", qty: 4 }]);
    expect(excessiveCheckResponse.status()).toBe(200);
    const excessiveCheckBody = await excessiveCheckResponse.json();
    await evidence.attachJson("over-refund-check.json", excessiveCheckBody);
    expect(excessiveCheckBody.verdict).toBe("OVER_REFUND");

    const submitRefund = (idempotencyKey: string, lines: Array<{ sku: string; qty: number }>) =>
      request.post(`${API_BASE_URL}/api/refunds`, {
        headers: { ...headers, "Idempotency-Key": idempotencyKey },
        data: { orderId: order.id, lines }
      });

    const idempotencyKey = `refund-${Date.now()}`;
    const initialRefundResponse = await submitRefund(idempotencyKey, [{ sku: "TEE", qty: 1 }]);
    expect(initialRefundResponse.status()).toBe(201);
    const initialRefundBody = await initialRefundResponse.json();
    await evidence.attachJson("first-refund.json", initialRefundBody);

    expect(initialRefundBody.lineAmountPaise).toBe(33300);
    expect(initialRefundBody.taxPaise).toBe(1665);
    expect(initialRefundBody.amountPaise).toBe(34965);
    const totalTaxShare = initialRefundBody.taxShares.reduce(
      (sum: number, share: number) => sum + share,
      0
    );
    expect(totalTaxShare).toBe(4995);

    const replayRefundResponse = await submitRefund(idempotencyKey, [{ sku: "TEE", qty: 1 }]);
    expect(replayRefundResponse.status()).toBe(200);
    const replayRefundBody = await replayRefundResponse.json();
    await evidence.attachJson("replayed-refund.json", replayRefundBody);
    expect(replayRefundBody.replayed).toBe(true);
    expect(replayRefundBody.refundId).toBe(initialRefundBody.refundId);

    const rejectedRefundResponse = await submitRefund(`refund-over-${Date.now()}`, [
      { sku: "TEE", qty: 4 }
    ]);
    expect(rejectedRefundResponse.status()).toBe(422);
    const rejectedRefundBody = await rejectedRefundResponse.json();
    await evidence.attachJson("rejected-over-refund.json", rejectedRefundBody);
    expect(rejectedRefundBody.verdict).toBe("OVER_REFUND");

    const finalOrderResponse = await request.get(`${API_BASE_URL}/api/refund-lab/orders/${order.id}`, {
      headers
    });
    expect(finalOrderResponse.status()).toBe(200);
    const finalOrder = await finalOrderResponse.json();
    await evidence.attachJson("final-refund-ledger.json", finalOrder);

    expect(finalOrder.status).toBe("PARTIALLY_REFUNDED");
    expect(finalOrder.refundCount).toBe(1);
    expect(finalOrder.refundableBalancePaise).toBe(69930);
    const teeLine = finalOrder.lines.find((line: { sku: string }) => line.sku === "TEE");
    expect(teeLine.refundedQty).toBe(1);

    await page.reload();
    await expect(page.getByTestId("refund-status")).toHaveText("PARTIALLY_REFUNDED");
    await expect(page.getByTestId("refund-count")).toHaveText("1");
    await expect(page.getByTestId("refund-balance-paise")).toHaveText("69930");
    await expect(page.getByTestId("refund-total-paise")).toHaveText("34965 paise");
    await expect(page.getByTestId("refunded-TEE")).toHaveText("1");
  });
});