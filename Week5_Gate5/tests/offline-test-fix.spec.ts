import {expect, test} from '..//fixtures/evidence';

test('Context set to offline after load', async ({ page, context, evidence }) => {
    await page.goto('/pos');

    await context.setOffline(true);

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

    evidence.response = body;
});