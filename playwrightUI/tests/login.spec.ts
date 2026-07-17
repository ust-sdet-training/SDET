import { test, expect } from '../fixtures/tripstack.fixture';

test.describe(' authentication', () => {

  test('logs in using environment-supplied credentials', async ({ page, loginPage }) => {
    await loginPage.goto();
    await loginPage.login();
    await expect(page.getByRole('link', { name: 'My Trips' })).toBeVisible();
  });
});
