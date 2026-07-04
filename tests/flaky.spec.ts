import { test, expect } from '@playwright/test';

test('flaky sync lab test', async ({ page }) => {
    // Intentional flaky test
    /*
    To fix: remove the waitForTimeOut
            remove textContent()
            assert it with .toHaveText
    */
    await page.goto('/sync-lab');

    await page.getByRole('button', { name: 'Load' }).click();
    await page.waitForTimeout(900);

    const text = await page.getByTestId('cart-count').textContent();
    expect.soft(text).toBe('3');
});