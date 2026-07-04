import { test, expect } from "../fixtures/app.fixture";
import { seedOrder, getLedger } from "../src/api/refund.api";

test("Verify refund calculations use exact paise values", async ({request,log,evidence,}) => {

  // Create a test order
  log.info("Creating order");
  const order = await seedOrder(request);
  evidence.order = order;

  // Verify the seeded order values
  expect(order.taxPaise).toBe(4995);

  expect(order.lines).toHaveLength(1);
  expect(order.lines[0].unitPaise).toBe(33300);
  expect(order.lines[0].qty).toBe(3);

  // Calculate expected values using integer paise
  const expectedLineAmount =order.lines[0].unitPaise * order.lines[0].qty;

  const expectedTotal =expectedLineAmount + order.taxPaise;

  // Verify the calculation
  expect(expectedLineAmount).toBe(99900);
  expect(expectedTotal).toBe(104895);

  // Verify values are whole numbers (no floating point)
  expect(Number.isInteger(expectedLineAmount)).toBeTruthy();
  expect(Number.isInteger(expectedTotal)).toBeTruthy();

  // Fetch the ledger
  const ledger = await getLedger(request, order.id);
  evidence.ledger = ledger;

  log.info("Ledger verified", {refundableBalance: ledger.refundableBalancePaise,});

  // Ledger total should match calculated total
  expect(ledger.refundableBalancePaise).toBe(expectedTotal);

});