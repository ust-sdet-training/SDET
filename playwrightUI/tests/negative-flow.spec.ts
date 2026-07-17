import { test, expect } from '../fixtures/tripstack.fixture';
import { SeatSelectionPage } from '../pages/seat-selection.page';

test.describe('negative UI paths', () => {
  test('rejects invalid login credentials', async ({ page, loginPage }) => {
    await loginPage.goto();
    await loginPage.loginWith('invalid@tripstack.test', 'wrong-password');

    await expect(page).toHaveURL(/\/login/);
    await expect(page.getByRole('alert')).toHaveText('Invalid email or password.');
  });

  test('redirects an unauthenticated traveller away from passenger details', async ({ page }) => {
    await page.goto('/book/passenger?type=bus&inventory=BUS-HYDBOM-03&seats=S1');

    await expect(page).toHaveURL(/\/login/);
    await expect(page.getByRole('button', { name: 'Sign in' })).toBeVisible();
  });

  test('does not allow a booked seat to be selected', async ({ page }) => {
    await page.goto('/buses/BUS-HYDBOM-03/seatmap');
    const bookedSeat = page.getByRole('button', { name: /^Seat .+ booked$/ }).first();

    await expect(bookedSeat).toHaveAttribute('aria-disabled', 'true');
    await expect(page.getByRole('button', { name: 'Continue to passenger details' })).toBeDisabled();
  });

  test('requires traveller details before continuing to payment', async ({ page, loginPage }) => {
    await loginPage.goto();
    await loginPage.login();
    await page.goto('/buses/BUS-HYDBOM-03/seatmap');

    const seats = new SeatSelectionPage(page);
    await seats.selectAvailableSeat();
    await seats.continueToPassengerDetails();
    await page.getByRole('button', { name: 'Continue to payment' }).click();

    await expect(page).toHaveURL(/\/book\/passenger/);
    await expect.poll(() => page.locator('input[name^="firstName_"]')
      .evaluate(input => (input as HTMLInputElement).validity.valid)).toBe(false);
  });
});
