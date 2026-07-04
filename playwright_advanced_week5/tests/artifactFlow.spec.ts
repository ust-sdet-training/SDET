import { test, expect } from "../fixtures/app.fixture";
 
import {
  seedOrder,
  refundOrder,
  checkRefund,
  getLedger,
  openReturnsPage,
  generateIdempotencyKey,
} from "../src/api/refund.api";
 
 
test.describe('FULL FLOW', () => {
 
  test('Partial Refund', async ({ request, page,log, evidence }) => {
 
        // Create a new order
    log.info("Order Seeding Process");

    const order = await seedOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Open returns page for the created order
    log.info("Return open page", { orderId });

    await openReturnsPage(page, orderId);

    // Check initial refund values on UI
    await expect(page.getByTestId('refund-line')).toContainText('₹333.00');
    await expect(page.getByTestId('refunded-TEE')).toHaveText('0');

    // Process partial refund
    log.info("Partial Refund");

    const partial = await refundOrder(request, orderId, 1, generateIdempotencyKey());

    expect(partial.status()).toBe(201);

    const partialJson = await partial.json();
    evidence.partialJson = partialJson;

    // Verify refund amount and tax values
    expect(partialJson.amountPaise).toBe(34965);
    expect(partialJson.lineAmountPaise).toBe(33300);
    expect(partialJson.taxPaise).toBe(1665);

    // Verify tax share total
    expect(partialJson.taxShares.reduce((sum: number, tax: number) =>
      sum + tax, 0)).toBe(4995);

    // Check tax values are whole numbers
    for (const tax of partialJson.taxShares) {
        expect(Number.isInteger(tax)).toBeTruthy();
    }

    // Get updated order details
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;
          log.info("Checking For Ledger", {
              status: ledger.status,
              refundCount: ledger.refundCount
          });
  
        await page.reload();
        await expect(page.getByTestId('refunded-TEE')).toHaveText('1');
        expect(ledger.status).toBe('PARTIALLY_REFUNDED');
        expect(ledger.refundCount).toBe(1);
        expect(ledger.refundableBalancePaise).toBe(69930);
        expect(ledger.lastRefund.amountPaise).toBe(34965);
 
    });
 
    test('Refund  Fully', async ({ request, page, log, evidence }) => {
 
       
    // Create a new order
    log.info("order createdion ");

    const order = await seedOrder(request);
    evidence.order = order;

    const orderId = order.id;

    // Process full refund
    log.info("Checking for the full Refund");

    const fully = await refundOrder(request, orderId, 3, generateIdempotencyKey("full"));

    expect(fully.status()).toBe(201);

          
    const fullyRefund = await fully.json();
    evidence.fullyRefund = fullyRefund;

    // Verify full refund amount
    expect(fullyRefund.amountPaise).toBe(104895);

    // Get updated order details
    const ledger = await getLedger(request, orderId);
    evidence.ledger = ledger;

          log.info("Full refund completed", {
          status: ledger.status
      });
 
        expect(ledger.status).toBe('REFUNDED');
        expect(ledger.refundableBalancePaise).toBe(0);
        expect(ledger.refundCount).toBe(1);
        expect(fullyRefund.lineAmountPaise).toBe(99900);
        expect(fullyRefund.taxPaise).toBe(4995);
        expect(ledger.lastRefund.amountPaise).toBe(104895);
 
        await openReturnsPage(page, orderId);
        await expect(page.getByTestId('refunded-TEE')).toHaveText('3');
        await expect(page.getByTestId('refund-total')).toHaveText('₹1,048.95');
 
    });
 
  const testCases = [
    {
      name: 'Valid refund',
      sku: 'TEE',
      qty: 1,
      expectedVerdict: 'APPROVED'
    },
    {
      name: 'Over refund',
      sku: 'TEE',
      qty: 4,
      expectedVerdict: 'OVER_REFUND'
    }
  ];
 
  for (const tc of testCases) {
 
    test(tc.name, async ({ request, log, evidence }) => {
 
        
// Create a new order for refund validation
    log.info("prder creation");

    const order = await seedOrder(request);
    evidence.order = order;

    // Check refund eligibility
    log.info("Checking Refund");

    const result = await checkRefund(
    request,
    order.id,
    tc.sku,
    tc.qty
    );

      
    evidence.refundCheck = result;

    // Verify refund verdict
    log.info("Checking Refund verdict", {
        verdict: result.verdict
    });

    expect(result.verdict).toBe(tc.expectedVerdict);

 
    });
 
  }
 
  test('Two money Refuse', async ({ request, log, evidence }) => {
 
    // Create a new order
log.info("Order Creation");

const order = await seedOrder(request);
evidence.order = order;

const orderId = order.id;

// Get order details before refund
const before = await getLedger(request, orderId);
evidence.ledgerBefore = before;

// Try over refund scenario
log.info("Over refund");

const overRefund = await refundOrder(request, orderId, 4, 'over-refund');

expect(overRefund.status()).toBe(422);

const error = await overRefund.json();
evidence.overRefundError = error;

// Verify over refund rejection
log.info("Over Refund Rejection", {
    reason: error.reason
});

// Make sure order balance is not changed
const after = await getLedger(request, orderId);

expect(after.refundableBalancePaise).toBe(before.refundableBalancePaise);

// Generate one idempotency key
const idempotencyKey = generateIdempotencyKey();

// First refund request
const refund1 = await refundOrder(request, orderId, 1, idempotencyKey);

expect(refund1.status()).toBe(201);

const refund = await refund1.json();
evidence.refund = refund;

// Send same request again
const refund2 = await refundOrder(request, orderId, 1, idempotencyKey);

expect(refund2.status()).toBe(200);

const replay = await refund2.json();
evidence.replayRefund = replay;

// Verify same refund is returned
expect(replay.refundId).toBe(refund.refundId);
expect(replay.amountPaise).toBe(refund.amountPaise);

// Get final ledger details
const ledger = await getLedger(request, orderId);
evidence.ledgerAfter = ledger;

// Verify only one refund exists in ledger
log.info("Refund with Idompotent", {
    refundCount: ledger.refundCount,
    balance: ledger.refundableBalancePaise
});
   
    expect(ledger.status).toBe('PARTIALLY_REFUNDED');
    expect(ledger.refundCount).toBe(1);
    expect(ledger.refundableBalancePaise).toBe(69930);
    expect(ledger.lastRefund.amountPaise).toBe(34965);
 
  });



test("retries queued sale with same idempotency key", async ({ page, context, log }) => {

  // Variables to track request attempts and successful syncs
  let attempts = 0;
  let successfulPosts = 0;

  // Store unique idempotency keys used during retries
  const keys = new Set<string>();

  // Intercept sales API requests
  await page.route("**/api/sales", async route => {

    // Allow non-POST requests to continue normally
    if (route.request().method() !== "POST") {
      return route.continue();
    }

    // Count API attempts
    attempts++;

    const key = route.request().headers()["idempotency-key"];

    // Verify idempotency key is present in the request
    expect(key).toBeDefined();

    // Store the key for later verification
    if (key) {
      keys.add(key);
    }

    // Fail the first request to simulate a network issue
    if (attempts === 1) {
      log.info("Simulating failed network request");
      return route.abort("failed");
    }

    // Count successful sync requests
    successfulPosts++;

    log.info("Retry request synced successfully");

    await route.fulfill({
      status: 201,
      contentType: "application/json",
      body: JSON.stringify({
        id: 9101,
        status: "SYNCED"
      })
    });
  });

  // Open the POS application
  log.info("Opening POS application");

  await page.goto("http://localhost:5173/pos");

  // Simulate offline mode
  log.info("Switching application to offline mode");

  await context.setOffline(true);

  // Queue a sale while offline
  log.info("Queueing sale while offline");

  await page.getByRole("button", { name: "Queue sale" }).click();

  // Verify the sale is added to the outbox queue
  await expect(page.getByTestId("outbox-count")).toHaveText("1");

  log.info("Sale added to outbox queue");

  // Restore network connection
  log.info("Restoring network connection");

  await context.setOffline(false);

  // Verify queued sale is synced and removed from outbox
  await expect(page.getByTestId("outbox-count")).toHaveText("0");

  log.info("Queued sale synced successfully");

  // Verify retry and idempotency details
  log.info("Verifying retry and idempotency checks", {
    attempts,
    successfulPosts,
    uniqueKeys: keys.size
  });

  // Verify one retry happened
  expect(attempts).toBe(2);

  // Verify only one successful sync was created
  expect(successfulPosts).toBe(1);

  // Verify the same idempotency key was used during retry
  expect(keys.size).toBe(1);
});  
 
});
 