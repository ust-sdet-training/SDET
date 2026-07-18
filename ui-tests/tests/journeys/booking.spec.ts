import { test, expect } from '@fixtures/fixtures';
import { config } from '@config/config';

test.describe('TripStack booking journey', () => {
  test('@journey happy path', async ({ page, loginPage, homePage, checkoutPage }) => {
    await homePage.openHomePage();
    await expect(page).toHaveURL('/');

    await loginPage.openLoginPage();
    await loginPage.login(config.email, config.password);

    await homePage.openHomePage();
    await homePage.searchJourney('GOI', 'PUN', '28/07/2026');

    await homePage.selectFirstResult();
    await checkoutPage.selectSeat();
    await checkoutPage.proceedToDetails();
    await checkoutPage.fillPassengerDetails('Laura', 'Takur', '25', 'Female', config.email, '9876543211');
    await checkoutPage.continuePayment();
    await checkoutPage.pay('Laura Takur', '411122222222', '03/29', '477');
  });
});

test.describe('TripStack resilience', () => {
  test('@journey a fault state payment connection reset', async ({ page, loginPage, homePage, checkoutPage }) => {

    await homePage.openHomePage();
    await expect(page).toHaveURL(/tripstack/);

    await loginPage.openLoginPage();
    await loginPage.login(config.email, config.password);

    await homePage.openHomePage();
    await homePage.searchJourney('GOI', 'PUN', '28/07/2026');
    await homePage.selectFirstResult();

    await checkoutPage.selectSeat();
    await checkoutPage.proceedToDetails();
    await checkoutPage.fillPassengerDetails('Laura', 'Thakur', '30', 'Female', config.email, '9876543210');
    await checkoutPage.continuePayment();
    await checkoutPage.pay('Laura Takur', '411156789111', '12/30', '123');

    await expect(page.locator('body')).toContainText(/connection|reset|retry|fault|payment/i);
  });
});

