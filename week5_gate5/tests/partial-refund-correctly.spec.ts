import { test, expect } from "@playwright/test";
import {
  seedOrder,
  refundOrder,
  generateIdempotencyKey
} from "../src/api/refund.api";

const cases = [
  {
    name: "valid refund",
    qty: 1,
    expectedStatus: 201,
    expectedAmount: 34965,
    expectedLineAmount: 33300,
    expectedTax: 1665
  },
  {
    name: "over_refund",
    qty: 4,
    expectedStatus: 422,
    expectedVerdict: "OVER_REFUND"
  },
  {
    name: "already_refunded",
    qty: 3,
    secondRefund: true,
    expectedStatus: 422,
    expectedVerdict: "ALREADY_REFUNDED"
  }
];

// Run the same test with different refund scenarios
for (const tc of cases) {
  test(tc.name, async ({ request }) => {
    const order = await seedOrder(request);

    // Reusable helper to submit a refund request
    const createRefund = (qty: number) =>
      refundOrder(
        request,
        order.id,
        qty,
        generateIdempotencyKey()
      );

    if (tc.secondRefund) {
      const first = await createRefund(tc.qty);
      expect(first.status()).toBe(201);

      // Second refund on the same order should be rejected
      const second = await createRefund(tc.qty);

      expect(second.status()).toBe(tc.expectedStatus);

      const body = await second.json();

      expect(body.verdict).toBe(tc.expectedVerdict);
      return;
    }

    const response = await createRefund(tc.qty);

    expect(response.status()).toBe(tc.expectedStatus);

    if (tc.expectedStatus === 201) {
      const refund = await response.json();

      // Verify refund amount and tax split
      expect(refund.amountPaise).toBe(tc.expectedAmount);
      expect(refund.lineAmountPaise).toBe(tc.expectedLineAmount);
      expect(refund.taxPaise).toBe(tc.expectedTax);

      expect(
        refund.taxShares.reduce(
          (sum: number, value: number) => sum + value,
          0
        )
      ).toBe(4995);
    } else {
      const body = await response.json();

      expect(body.verdict).toBe(tc.expectedVerdict);
    }
  });
}