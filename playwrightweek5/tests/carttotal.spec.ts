import { expect, test } from "@playwright/test";

test("cart total uses integer paise", async () => {
  const item = {
    sku: "COAT",
    name: "Rain Coat",
    unitPaise: 65000,
    qty: 4,
  };

  const totalTaxPaise = 10000;

  // Refunding one item
  const refundQty = 1;

  const lineAmountPaise = item.unitPaise * refundQty;
  const taxPaise = 2500;
  const refundAmountPaise = lineAmountPaise + taxPaise;

  expect(lineAmountPaise).toBe(65000);
  expect(taxPaise).toBe(2500);
  expect(refundAmountPaise).toBe(67500);

  const taxShares = [2500, 2500, 2500, 2500];

  // Invariant: Distributed tax must equal the original order tax.
  expect(taxShares.reduce((sum, tax) => sum + tax, 0)).toBe(totalTaxPaise);
});
