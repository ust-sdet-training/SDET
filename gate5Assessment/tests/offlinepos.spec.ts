import { test, expect } from '../fixtures/pos';
import type { CDPSession } from '@playwright/test';

test.describe('Week 5 - Gate 5 Resilient POS Behaviour', () => {

  // Start tracing before every test.
  // The trace helps us investigate failures by recording screenshots,
  // DOM snapshots and source files.
  test.beforeEach(async ({ context }) => {
    await context.tracing.start({
      screenshots: true,
      snapshots: true,
      sources: true,
    });
  });

  // Stop tracing after each test and attach the trace to the report.
  test.afterEach(async ({ context }, testInfo) => {
    const traceFile = testInfo.outputPath('trace.zip');

    await context.tracing.stop({
      path: traceFile,
    });

    await testInfo.attach('Execution Trace', {
      path: traceFile,
      contentType: 'application/zip',
    });
  });

  test('Should store sales in queue while offline and sync automatically after reconnect', async ({
    page,
    context,
    log,
    evidence,
  }) => {

    // Record every failed network request.
    page.on('requestfailed', request => {
      evidence.requestFailures.push(
        `${request.method()} ${request.url()} : ${request.failure()?.errorText}`
      );

      log.warn('Network request failed', {
        url: request.url(),
      });
    });

    log.info('Opening POS application');
    await page.goto('/pos');

    // Simulate internet disconnection.
    log.info('Turning network OFF');
    await context.setOffline(true);

    // User should immediately know that the application is offline.
    await expect(page.getByTestId('network-banner')).toContainText('Offline');

    // Browser API should also report offline.
    expect(await page.evaluate(() => navigator.onLine)).toBe(false);

    // Queue a sale.
    // Instead of sending it to the server immediately,
    // it should remain inside the local queue.
    log.info('Creating a sale while offline');

    await page.getByRole('button', {
      name: 'Queue sale',
    }).click();

    await expect(page.getByTestId('outbox-status'))
      .toHaveText('pending');

    await expect(page.getByTestId('outbox-count'))
      .toHaveText('1');

    // Restore internet connection.
    log.info('Turning network ON');
    await context.setOffline(false);

    // The queued request should automatically sync.
    await expect(page.getByTestId('network-banner'))
      .toContainText('Online');

    expect(await page.evaluate(() => navigator.onLine))
      .toBe(true);

    await expect(page.getByTestId('outbox-count'))
      .toHaveText('0');

    log.info('Queued sale synced successfully');

    await printFailures(evidence, log);
  });

  test('Should rollback optimistic queue when the server request fails', async ({
    page,
    log,
    evidence,
  }) => {

    page.on('requestfailed', request => {
      evidence.requestFailures.push(
        `${request.method()} ${request.url()} : ${request.failure()?.errorText}`
      );
    });

    // Abort every sale request.
    // This simulates a real network failure.
    await page.route('**/api/sales', async route => {

      log.info('Forcing request failure');

      await new Promise(resolve => setTimeout(resolve, 300));

      await route.abort('failed');
    });

    await page.goto('/pos');

    // Create a new sale.
    await page.getByRole('button', {
      name: 'Queue sale',
    }).click();

    // Initially the UI shows an optimistic pending state.
    await expect(page.getByTestId('outbox-status'))
      .toHaveText('pending');

    // Since the request failed,
    // the application should rollback the optimistic update.
    await expect(page.getByTestId('outbox-count'))
      .toHaveText('0');

    expect(evidence.requestFailures.length)
      .toBeGreaterThan(0);

    log.info('Rollback verified successfully');
  });

  test('Should keep pending state until delayed server response completes', async ({
    page,
    log,
  }) => {

    // Delay every request before sending it to the backend.
    await page.route('**/api/sales', async route => {

      log.info('Adding artificial network delay');

      await new Promise(resolve => setTimeout(resolve, 2000));

      await route.continue();
    });

    await page.goto('/pos');

    await page.getByRole('button', {
      name: 'Queue sale',
    }).click();

    // The request is still travelling,
    // so pending status should remain visible.
    await expect(page.getByTestId('outbox-status'))
      .toHaveText('pending');

    // After the delayed response arrives,
    // queue should become empty.
    await expect(page.getByTestId('outbox-count'))
      .toHaveText('0', {
        timeout: 5000,
      });

    log.info('Delayed request completed successfully');
  });

test('Should process queue successfully using mocked backend response', async ({
  page,
  log,
  evidence,
}) => {

  // Intercept the sales API and return a mocked response.
  // This avoids calling the real backend during the test.
  await page.route('**/api/sales', async route => {

    // Create a custom mocked response.
    const mockSaleResponse = {
      saleId: `SALE-${Date.now()}`,
      orderNumber: 'ORD-1001',
      status: 'completed',
      message: 'Sale processed successfully',
      syncedAt: new Date().toISOString(),
    };

    // Save the mocked response for evidence.
    evidence.cartResponse = mockSaleResponse;

    // Return the mocked response.
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify(mockSaleResponse),
    });

    log.info('Mock API response returned successfully');
  });

  // Open the POS application.
  await page.goto('/pos');

  // Queue a sale.
  await page.getByRole('button', {
    name: 'Queue sale',
  }).click();

  // Verify the queue becomes empty after successful sync.
  await expect(page.getByTestId('outbox-count'))
    .toHaveText('0');

  log.info('Mock response verified');
});

  test('Should eventually sync queued sale under slow network conditions', async ({
    page,
    context,
    browserName,
    log,
  }) => {

    test.skip(
      browserName !== 'chromium',
      'Network throttling requires Chromium'
    );

    // CDP allows us to simulate poor network conditions.
    const client: CDPSession =
      await context.newCDPSession(page);

    await client.send('Network.enable');

    await client.send(
      'Network.emulateNetworkConditions',
      {
        offline: false,
        latency: 400,
        downloadThroughput: (500 * 1024) / 8,
        uploadThroughput: (250 * 1024) / 8,
      }
    );

    log.info('Slow network profile applied');

    await page.goto('/pos');

    await page.getByRole('button', {
      name: 'Queue sale',
    }).click();

    // Slow network should keep the request pending.
    await expect(page.getByTestId('outbox-status'))
      .toHaveText('pending');

    // Eventually the request should complete.
    await expect(page.getByTestId('outbox-count'))
      .toHaveText('0', {
        timeout: 10000,
      });

    log.info('Sale synchronized successfully over slow connection');
  });
});

async function printFailures(
  evidence: any,
  log: any,
) {

  // Print failed requests if any were captured.
  if (evidence.requestFailures.length > 0) {

    log.warn('Failed network requests found', {
      total: evidence.requestFailures.length,
    });
  }
}