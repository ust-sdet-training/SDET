import { test, expect } from "../../fixtures/app.fixture";

import {
  seedOrder,
  checkRefund,
} from "../../src/api/refund.api";

test.describe("Refund Eligibility", () => {

  const testCases = [
    {
      name: "Valid Refund",
      sku: "TEE",
      qty: 1,
      expectedVerdict: "APPROVED",
    },
    {
      name: "Over Refund",
      sku: "TEE",
      qty: 4,
      expectedVerdict: "OVER_REFUND",
    },
  ];

  for (const tc of testCases) {

    test(tc.name, async ({ request, log, evidence }) => {

      // Create a fresh order for the refund eligibility check.
      log.info("Creating order");
      const order = await seedOrder(request);
      evidence.order = order;

      // Verify whether the requested refund quantity is eligible.
      log.info("Checking refund eligibility");

      const result = await checkRefund(
        request,
        order.id,
        tc.sku,
        tc.qty
      );

      // Store the eligibility response as evidence.
      evidence.refundCheck = result;

      log.info("Refund eligibility verified", {
        verdict: result.verdict,
      });

      // Validate the refund eligibility verdict.
      expect(result.verdict).toBe(tc.expectedVerdict);

    });

  }

});