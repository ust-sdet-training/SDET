
import { expect }
from "@playwright/test";

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

test(
  "prevent over refund money leak",

  async ({
    request,
    evidence
  }) => {

    const order =
      await createRefundLabOrder(
        request
      );

    const ledgerBefore =
      await getLedger(
        request,
        order.id
      );

    evidence.ledgerBefore =
      ledgerBefore;

    const payload = {

      orderId: order.id,

      lines: [
        {
          sku: "TEE",

          qty: 4
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
                "invalid"
              )
          },

          data: payload
        }
      );

    expect(response.status())
      .toBe(422);

    const body =
      await response.json();

    evidence.refundResponse =
      body;

    expect(
      body.reason
    ).toBe(
      "OVER_REFUND"
    );

    const ledgerAfter =
      await getLedger(
        request,
        order.id
      );

    evidence.ledgerAfter =
      ledgerAfter;

    expect(
      ledgerAfter
      .refundableBalancePaise
    ).toBe(
      ledgerBefore
      .refundableBalancePaise
    );

    expect(
      ledgerAfter
      .refundCount
    ).toBe(
      ledgerBefore
      .refundCount
    );
  }
);

test(
  "prevent duplicate refund payout",

  async ({
    request,
    evidence
  }) => {

    const order =
      await createRefundLabOrder(
        request
      );

    const idemKey =
      correlationId(
        "refund"
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

    const firstRefund =
      await request.post(
        `${posApiUrl}/api/refunds`,
        {
          headers: {

            ...authHeaders,

            "Idempotency-Key":
              idemKey
          },

          data: payload
        }
      );

    expect(
      firstRefund.status()
    ).toBe(201);

    const secondRefund =
      await request.post(
        `${posApiUrl}/api/refunds`,
        {
          headers: {

            ...authHeaders,

            "Idempotency-Key":
              idemKey
          },

          data: payload
        }
      );

    expect(
      secondRefund.status()
    ).toBe(200);

    const body =
      await secondRefund.json();

    evidence.refundResponse =
      body;

    const ledger =
      await getLedger(
        request,
        order.id
      );

    evidence.ledgerAfter =
      ledger;

    expect(
      ledger.refundCount
    ).toBe(1);

    expect(
      ledger
      .lastRefund
      .amountPaise
    ).toBe(34965);
  }
);

