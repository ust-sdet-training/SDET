import { test, expect } from "../fixtures/evidence";
import crypto from "node:crypto";

// Centralize the API base URL so the test is easy to move between environments.
const BASE_URL = "http://localhost:4000";

// Common authentication used by every refund API in this test.
const AUTH_HEADERS = {
  Authorization: "Bearer demo-token-1-customer",
};

test("Partial refund with tax proration", async ({ request, log, evidence }) => {
  // ---------------------------------------------------------------------------
  // STEP 1: Creattion of a fresh order for this test.
  // Using a new order every time keeps the test isolated and avoids interference
  // from previous executions. 
  // ---------------------------------------------------------------------------
  const seedResponse = await request.post(
    `${BASE_URL}/api/refund-lab/orders`,
    {
      headers: {
        ...AUTH_HEADERS,
        "Content-Type": "application/json",
      },
      data: {
        taxPaise: 4995,
        lines: [
          {
            sku: "TEE",
            name: "Training Tee",
            unitPaise: 33300,
            qty: 3,
          },
        ],
      },
    }
  );

  // This first API call exists to create a clean, isolated order so the refund assertions are not affected by any prior ledger state.
  log.info("Seeding a fresh refund-lab order", {
    status: seedResponse.status(),
    endpoint: `${BASE_URL}/api/refund-lab/orders`,
  });

  // Order creation must succeed before continuing with refund validation.
  expect(seedResponse.status()).toBe(201);

  const order = await seedResponse.json();

  // Verify the seeded order starts in the expected business state.
  expect(order.status).toBe("PAID");
  expect(order.refundableBalancePaise).toBe(104895);

  // ---------------------------------------------------------------------------
  // STEP 2: Check refund eligibility.
  // A refund should never be attempted until the backend confirms that the
  // requested items are eligible.
  // ---------------------------------------------------------------------------
  const eligibilityResponse = await request.post(
    `${BASE_URL}/api/refunds/check`,
    {
      headers: {
        ...AUTH_HEADERS,
        "Content-Type": "application/json",
      },
      data: {
        orderId: order.id,
        lines: [
          {
            sku: "TEE",
            qty: 1,
          },
        ],
      },
    }
  );

  // This validation matters because a refund should only proceed when the business rules explicitly approve the requested line item.
  log.info("Checking refund eligibility", {
    status: eligibilityResponse.status(),
    orderId: order.id,
  });

  expect(eligibilityResponse.status()).toBe(200);

  const eligibility = await eligibilityResponse.json();

  // Ensure the backend explicitly approves this refund request.
  expect(eligibility.verdict).toBe("APPROVED");
  expect(eligibility.reason).toBeNull();

  // ---------------------------------------------------------------------------
  // STEP 3: Perform the partial refund.
  // A unique Idempotency-Key protects against accidental duplicate refunds if
  // the same request is retried.
  // ---------------------------------------------------------------------------
  const refundResponse = await request.post(
    `${BASE_URL}/api/refunds`,
    {
      headers: {
        ...AUTH_HEADERS,
        "Content-Type": "application/json",
        "Idempotency-Key": crypto.randomUUID(),
      },
      data: {
        orderId: order.id,
        lines: [
          {
            sku: "TEE",
            qty: 1,
          },
        ],
      },
    }
  );

  // This refund step is the core business action, and it is guarded by an idempotency key so duplicate retries do not create duplicate ledger entries.
  log.info("Submitting the partial refund request", {
    status: refundResponse.status(),
    orderId: order.id,
  });

  expect(refundResponse.status()).toBe(201);

  const refund = await refundResponse.json();

  // Validate every money calculation using integer paise to avoid rounding issues.
  expect(refund.lineAmountPaise).toBe(33300);
  expect(refund.taxPaise).toBe(1665);
  expect(refund.amountPaise).toBe(34965);
  expect(refund.refundCount).toBe(1);

  // Tax shares should always add back to the original order tax.
  // This protects against rounding errors during tax proration.
  expect(
    refund.taxShares.reduce(
      (sum: number, tax: number) => sum + tax,
      0
    )
  ).toBe(4995);

  // ---------------------------------------------------------------------------
  // STEP 4: Read the latest order state from the ledger.
  // The ledger is treated as the source of truth to confirm that the refund
  // permanently updated the order.
  // ---------------------------------------------------------------------------
  const ledgerResponse = await request.get(
    `${BASE_URL}/api/refund-lab/orders/${order.id}`,
    {
      headers: AUTH_HEADERS,
    }
  );

  // This ledger read is important because it verifies the refund persisted in the system-of-record state rather than only in the immediate response payload.
  log.info("Reading the updated ledger state", {
    status: ledgerResponse.status(),
    orderId: order.id,
  });

  expect(ledgerResponse.status()).toBe(200);

  const ledger = await ledgerResponse.json();

  // Confirm that the refund is reflected correctly in the order history.
  expect(ledger.status).toBe("PARTIALLY_REFUNDED");
  expect(ledger.refundCount).toBe(1);
  expect(ledger.refundableBalancePaise).toBe(69930);

  // Cross-check the ledger against the refund response to ensure both APIs
  // report the same refund transaction.
  expect(ledger.lastRefund.refundId).toBe(refund.refundId);
  expect(ledger.lastRefund.amountPaise).toBe(34965);

  evidence["partial-refund"] = {
    orderId: order.id,
    refundId: refund.refundId,
    refundAmountPaise: refund.amountPaise,
    refundCount: refund.refundCount,
    refundableBalancePaise: ledger.refundableBalancePaise,
  };
});