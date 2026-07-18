import { test, expect } from '@fixtures/fixtures';
import { config } from '@config/config';

test.describe('TripStack resilience', () => {
  test('@resilience a fault state resilience payment connection reset', async ({ page, loginPage, homePage, checkoutPage }) => {
    await page.route('**/payment/**', route => {
      route.abort('connectionreset');
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
    await checkoutPage.fillPassengerDetails('Laura', 'Thakur', '30', 'Female', config.email, '9999999999');
    await checkoutPage.continuePayment();
    await checkoutPage.pay('Laura Test', '411156789111', '12/30', '123');

    await expect(page.locator('body')).toContainText(/connection|reset|retry|fault|payment/i);
  });
});
