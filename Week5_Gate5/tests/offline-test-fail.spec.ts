import {expect, test} from '@playwright/test';

test('Context set to offline before load', async ({ page, context }) => {
    await context.setOffline(true);

    await page.goto('/pos');

    await page.getByRole('button', {name: 'Queue sale'}).click();

    await expect(page.getByTestId('outbox-count')).toHaveText('1');

    const responsePromise = page.waitForResponse(
        response =>
            response.url().includes('/api/sales') &&
            response.status() === 201
    );

    await context.setOffline(false);

    const response = await responsePromise;

    const body = await response.json();

    expect(body.status).toBe('SYNCED');

    await expect(page.getByTestId('outbox-count')).toHaveText('0');
});