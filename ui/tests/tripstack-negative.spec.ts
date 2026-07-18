import { test, expect } from '../fixtures/playwrightFixtures';
import { APP_BASE_URL, APP_PATHS } from '../constants';
import { getTestData } from './testData';
import { authLogin, getMe, resetNamespace } from './apiHelpers';

test.describe('TripStack negative UI checks', () => {
  test('@smoke shows an error for invalid login credentials', async ({ loginPage, logger, page }) => {
    const { credentials } = getTestData();
    logger.info('Negative test started: invalid login');

    await loginPage.goto();
    await loginPage.login(credentials.email, 'wrong-password');

    await expect(page.getByText(/invalid|incorrect|email or password/i)).toBeVisible({ timeout: 10_000 });
    logger.info('Negative test completed: invalid login handled');
  });

  test('@smoke blocks access to protected pages for an unauthenticated user', async ({ page, logger }) => {
    logger.info('Negative test started: unauthenticated access');
    await page.goto(`${APP_BASE_URL}${APP_PATHS.busSearch}`);

    await expect(page.locator('body')).toContainText(/login|sign in|log in/i);
    logger.info('Negative test completed: protected page redirected');
  });

  test('rejects an expired bearer token on auth/me', async ({ request, logger }) => {
    const { credentials } = getTestData();
    logger.info('Negative test started: expired bearer token');

    const loginResponse = await authLogin(request, credentials.email, credentials.password, 1);
    expect(loginResponse.status()).toBe(200);
    const loginBody = await loginResponse.json();
    const token = String(loginBody.token ?? '');
    expect(token).toMatch(/^[A-Za-z0-9-_]+\.[A-Za-z0-9-_]+\.[A-Za-z0-9-_]+$/);

    await new Promise((resolve) => setTimeout(resolve, 16_00));

    const expiredResponse = await getMe(request, token);
    expect(expiredResponse.status()).toBe(401);
    const expiredBody = await expiredResponse.json();
    expect(expiredBody.error).toMatch(/unauthorized|expired/i);
    logger.info('Negative test completed: expired bearer token rejected');
  });

  test('rejects a tampered bearer token on auth/me', async ({ request, logger }) => {
    const { credentials } = getTestData();
    logger.info('Negative test started: tampered bearer token');

    const loginResponse = await authLogin(request, credentials.email, credentials.password);
    expect(loginResponse.status()).toBe(200);
    const loginBody = await loginResponse.json();
    const token = String(loginBody.token ?? '');
    const tamperedToken = token.replace(/.$/, token.slice(-1) === 'A' ? 'B' : 'A');

    const tamperedResponse = await getMe(request, tamperedToken);
    expect(tamperedResponse.status()).toBe(401);
    const tamperedBody = await tamperedResponse.json();
    expect(tamperedBody.error).toMatch(/unauthorized|invalid/i);
    logger.info('Negative test completed: tampered bearer token rejected');
  });

  test('calls self-serve reset and clears the employee namespace', async ({ request, logger }) => {
    const { credentials, empId } = getTestData();
    logger.info('Negative test started: reset endpoint');

    const loginResponse = await authLogin(request, credentials.email, credentials.password);
    expect(loginResponse.status()).toBe(200);
    const loginBody = await loginResponse.json();
    const token = String(loginBody.token ?? '');

    const resetResponse = await resetNamespace(request, token);
    expect(resetResponse.status()).toBe(200);
    const resetBody = await resetResponse.json();
    expect(resetBody.emp).toBe(empId);
    expect(resetBody.purged).not.toBeUndefined();
    logger.info('Negative test completed: namespace reset succeeded', { empId, purged: resetBody.purged });
  });
});
