import { test, expect } from '../fixtures/playwrightFixtures';
import { APP_BASE_URL, APP_PATHS } from '../constants';
import { getTestData } from './testData';

test.describe('TripStack negative UI checks', () => {
  test('shows an error for invalid login credentials', async ({ loginPage, logger, page }) => {
    const { credentials } = getTestData();
    logger.info('Negative test started: invalid login');

    await loginPage.goto();
    await loginPage.login(credentials.email, 'wrong-password');

    await expect(page.getByText(/invalid|incorrect|email or password/i)).toBeVisible({ timeout: 10_000 });
    logger.info('Negative test completed: invalid login handled');
  });

  test('blocks access to protected pages for an unauthenticated user', async ({ page, logger }) => {
    logger.info('Negative test started: unauthenticated access');
    await page.goto(`${APP_BASE_URL}${APP_PATHS.busSearch}`);

    await expect(page.locator('body')).toContainText(/login|sign in|log in/i);
    logger.info('Negative test completed: protected page redirected');
  });
});
