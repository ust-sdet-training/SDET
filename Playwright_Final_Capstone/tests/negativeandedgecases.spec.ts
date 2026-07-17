import { expect, test } from '../fixtures/testFixtures';

test.describe('Flight booking regression and edge scenarios', () => {
  test('shows a validation message for invalid credentials', async ({ appPages }) => {
    await appPages.login.open();
    await appPages.login.login('bad@example.com', 'wrong-password');

    await expect(appPages.login.errorMessage()).toContainText(/invalid email or password/i);
  });

  test('can navigate the calendar to a future month and select a date', async ({ appPages, user }) => {
    await appPages.login.open();
    await appPages.login.login(user.email, user.password);
    await appPages.flightSearch.open();
    await appPages.flightSearch.selectRoute('MAA', 'HYD');
    await appPages.flightSearch.selectDepartureDate(25);

    await expect(appPages.flightSearch.selectedDateReadout()).not.toHaveText('none');
    await expect(appPages.flightSearch.dateInputValue()).toHaveValue(/.+/);
  });

  test('shows no flights for the selected no-results date', async ({ appPages, user }) => {
    await appPages.login.open();
    await appPages.login.login(user.email, user.password);
    await appPages.flightSearch.open();
    await appPages.flightSearch.selectRoute('MAA', 'HYD');
    await appPages.flightSearch.selectDateByLabel('Wednesday, 1 July');
    await appPages.flightSearch.search();

    await expect(appPages.flightResults.flight('Vistara UK-174')).not.toBeVisible();
  });
});
