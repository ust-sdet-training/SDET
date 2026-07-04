import { expect, test } from "@playwright/test";

test('Testing if the UI will fail when an actual failure happens', async ({ page }) => {
    // Simulate a failed sales API request
    await page.route('**/api/sales', async (route) => {
        await new Promise(resolve => setTimeout(resolve, 250));
        await route.abort();
    });

    await page.goto('http://localhost:5173/pos');
    await page.getByRole('button', { name: 'Queue sale' }).click();

    // Verify rollback message is shown
    await expect(page.getByTestId('pos-sync-status'))
        .toContainText('Sync failed. Optimistic sale rolled back.');

    await expect(page.getByTestId('outbox-count')).toHaveText('0');

    // Sale should not remain in the UI
    const row = page.getByRole('row', { name: /Running Shoes/i });
    await expect(row).toHaveCount(0);
});