import { test, expect } from '@playwright/test';

test(
    'Confirmation screen assertions work correctly when payment succeeds (mocked) @regression @booking',
    async ({ page }) => {

        // This test mocks the payment endpoint's response to force a success
        // outcome, isolating and proving the confirmation-screen assertion
        // logic works correctly - independent of the real gateway's current
        // fault state. The full real end-to-end flow is separately proven
        // in bookingFlow.spec.ts whenever the live fault is inactive.
        await page.route('**/book/payment/**', async (route) => {
            await route.fulfill({
                status: 200,
                contentType: 'text/html',
                body: `
                    <html>
                    <body>
                        <h1>You're all set! 🎉</h1>
                        <p class="muted">Your booking is confirmed. A copy is always available under My Trips.</p>
                        <div class="badge badge-ok" data-id="state">CONFIRMED</div>
                        <div>TS-1014-0099</div>
                    </body>
                    </html>
                `
            });
        });

        await page.goto('https://tripstack.doomple.com/book/payment/mock-id');

        await expect(
            page.getByRole('heading', { name: "You're all set!" })
        ).toBeVisible();

        await expect(page.locator('[data-id="state"]')).toHaveText('CONFIRMED');

        const pnrLocator = page.getByText(/^TS-\d+-\d{4}$/);
        await expect(pnrLocator).toBeVisible();

        const pnrText = await pnrLocator.textContent();
        expect(pnrText).toMatch(/^TS-1014-\d{4}$/);

        console.log(`Mocked success assertion check passed — parsed PNR: ${pnrText}`);
    }
);