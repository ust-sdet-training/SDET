import { test, expect } from '@fixtures/fixtures';
import { config } from '@config/config';

test.describe('TripStack negatives', () => {
  test('@negative invalid login is rejected', async ({ page, loginPage }) => {
    await loginPage.openLoginPage();
    await loginPage.login('wrong@tripstack.com', 'wrongPassword@123');

    await expect(page.locator('body')).toContainText(/invalid|error|login|retry/i);
  });

  test('@negative payment bypass is blocked', async ({ page, loginPage, homePage, checkoutPage }) => {
    await page.route('**/payment/**', route => {
      route.fulfill({
        status: 402,
        contentType: 'application/json',
        body: JSON.stringify({ error: 'payment declined' })
      });
    });

    await homePage.openHomePage();
    await expect(page).toHaveURL(/tripstack/);

    await loginPage.openLoginPage();
    await loginPage.login(config.email, config.password);

    await homePage.openHomePage();
    await homePage.searchJourney('GOI', 'PUN', '28/07/2026');
    await homePage.selectFirstResult();

    await checkoutPage.selectSeat();
    await checkoutPage.proceedToDetails();
    await checkoutPage.fillPassengerDetails('Laura', 'Test', '30', 'Female', config.email, '9999999999');
    await checkoutPage.continuePayment();
    await checkoutPage.pay('Laura Test', '4111111111111111', '12/30', '123');

    await expect(page.locator('body')).toContainText(/declined|payment failed|error|retry/i);
  });
});
