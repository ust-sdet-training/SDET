import { test, expect } from "@playwright/test";
import {
  seedOrder,
  refundOrder,
  getLedger,
  generateIdempotencyKey
} from "../src/api/refund.api";

test("over refund keeps balance unchanged and idempotent replay", async ({ request }) => {
  const order = await seedOrder(request);

  const before = await getLedger(request, order.id);

  // Try to refund more than the available quantity
  const overRefund = await refundOrder(
    request,
    order.id,
    4,
    generateIdempotencyKey("over")
  );

  expect(overRefund.status()).toBe(422);

  const overBody = await overRefund.json();
  expect(overBody.verdict).toBe("OVER_REFUND");

  const afterFailure = await getLedger(request, order.id);

  // Balance should not change after a rejected refund
  expect(afterFailure.refundableBalancePaise).toBe(
    before.refundableBalancePaise
  );

  const key = generateIdempotencyKey();

  const firstRefund = await refundOrder(
    request,
    order.id,
    1,
    key
  );

  expect(firstRefund.status()).toBe(201);

  // Replay the same request using the same idempotency key
  const secondRefund = await refundOrder(
    request,
    order.id,
    1,
    key
  );


  expect(secondRefund.status()).toBe(200);

  const replay = await secondRefund.json();

  expect(replay.replayed).toBe(true);

  const finalOrder = await getLedger(request, order.id);

  // Only one refund should be recorded
  expect(finalOrder.refundCount).toBe(1);
});