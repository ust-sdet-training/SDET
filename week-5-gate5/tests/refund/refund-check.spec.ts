import { test, expect } from "../../fixtures/app.fixture";

import {
  seedOrder,
  checkRefund,
} from "../../src/refund.api";

// Cover both valid and over-refund scenarios
const testCases = [
  {
    name: "Valid refund",
    sku: "TEE",
    qty: 1,
    expectedVerdict: "APPROVED",
  },
  {
    name: "Over refund",
    sku: "TEE",
    qty: 4,
    expectedVerdict: "OVER_REFUND",
  },
];

test.describe("Refund Eligibility", () => {
  for (const tc of testCases) {
    test(tc.name, async ({ request, log, evidence }) => {
      log.info("Creating order");
      const order = await seedOrder(request);
      evidence.order = order;
      log.info("Checking refund eligibility");
      const result = await checkRefund(
        request,
        order.id,
        tc.sku,
        tc.qty
      );
      evidence.refundCheck = result;
      log.info("Refund verdict", {verdict: result.verdict,});
      // Verify the refund request gets the expected verdict
      expect(result.verdict).toBe(tc.expectedVerdict);
    });
  }
});