
import { expect } from "@playwright/test";

import { authHeaders }
from "./auth";

import { posApiUrl }
from "./refund-api";

export async function getLedger(
  request: any,
  orderId: number
) {

  const response =
    await request.get(
      `${posApiUrl}/api/refund-lab/orders/${orderId}`,
      {
        headers: authHeaders
      }
    );

  expect(response.status())
    .toBe(200);

  return await response.json();
}

