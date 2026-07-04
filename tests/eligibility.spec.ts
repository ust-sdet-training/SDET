
import { expect }
from "@playwright/test";

import { test }
from "./fixtures/refund-evidence";

import { authHeaders }
from "./helpers/auth";

import { posApiUrl }
from "./helpers/refund-api";

import {
  eligibilityCases
}
from "./data/eligibility-cases";

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

for (
  const testCase
  of eligibilityCases
) {

  test(
    `eligibility - ${testCase.name}`,

    async ({
      request,
      evidence
    }) => {

      const order =
        await createRefundLabOrder(
          request
        );

      const response =
        await request.post(
          `${posApiUrl}/api/refunds/check`,
          {
            headers:
              authHeaders,

            data: {

              orderId:
                order.id,

              lines: [
                {
                  sku: "TEE",

                  qty:
                    testCase.qty
                }
              ]
            }
          }
        );

      expect(response.status())
        .toBe(200);

      const body =
        await response.json();

      evidence.eligibilityResult =
        body;

      expect(
        body.verdict
      ).toBe(
        testCase.verdict
      );
    }
  );
}

