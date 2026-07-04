import { expect } from "@playwright/test";

import { test }
from "./fixtures/refund-evidence";

import { authHeaders }
from "./helpers/auth";

import { posApiUrl }
from "./helpers/refund-api";

import { getLedger }
from "./helpers/ledger-helper";

import { correlationId }
from "./helpers/correlation";

// --------------------------------------------------
// Create Refund Lab Order
// --------------------------------------------------

async function createRefundLabOrder(
  request: any
) {

  const response =
    await request.post(
      `${posApiUrl}/api/refund-lab/orders`,
      {
        headers: authHeaders,

        data: {

          taxPaise: 4995,

          lines: [
            {
              sku: "TEE",

              name:
                "Training Tee",

              unitPaise:
                33300,

              qty: 3
            }
          ]
        }
      }
    );

  expect(response.status())
    .toBe(201);

  return await response.json();
}

// ======================================================
// PARTIAL REFUND
// ======================================================

test(
  "partial refund - amount + invariant",

  async ({
    request,
    evidence
  }) => {

    const order =
      await createRefundLabOrder(
        request
      );

    const payload = {

      orderId: order.id,

      lines: [
        {
          sku: "TEE",

          qty: 1
        }
      ]
    };

    evidence.refundRequest =
      payload;

    const response =
      await request.post(
        `${posApiUrl}/api/refunds`,
        {
          headers: {

            ...authHeaders,

            "Idempotency-Key":
              correlationId(
                "refund"
              )
          },

          data: payload
        }
      );

    expect(response.status())
      .toBe(201);

    const body =
      await response.json();

    evidence.refundResponse =
      body;

    // Refund amount
    expect(
      body.amountPaise
    ).toBe(34965);

    // Balancing invariant
    expect(
      body.taxShares.reduce(
        (
          a: number,
          x: number
        ) => a + x,
        0
      )
    ).toBe(4995);

    // Ledger verification
    const ledger =
      await getLedger(
        request,
        order.id
      );

    evidence.ledgerAfter =
      ledger;

    expect(
      ledger.status
    ).toBe(
      "PARTIALLY_REFUNDED"
    );
  }
);

// ======================================================
// FULL REFUND
// ======================================================

test(
  "full refund exact paise",

  async ({
    request,
    evidence
  }) => {

    const order =
      await createRefundLabOrder(
        request
      );

    const payload = {

      orderId: order.id,

      lines: [
        {
          sku: "TEE",

          qty: 3
        }
      ]
    };

    evidence.refundRequest =
      payload;

    const response =
      await request.post(
        `${posApiUrl}/api/refunds`,
        {
          headers: {

            ...authHeaders,

            "Idempotency-Key":
              correlationId(
                "full"
              )
          },

          data: payload
        }
      );

    expect(response.status())
      .toBe(201);

    const body =
      await response.json();

    evidence.refundResponse =
      body;

    // Full refund amount
    expect(
      body.amountPaise
    ).toBe(104895);

    // Ledger verification
    const ledger =
      await getLedger(
        request,
        order.id
      );

    evidence.ledgerAfter =
      ledger;

    expect(
      ledger.status
    ).toBe("REFUNDED");
  }
);

