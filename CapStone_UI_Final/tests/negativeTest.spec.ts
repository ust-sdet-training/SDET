import { test, expect } from '../fixture/index';

test.describe('Negative tests for bus booking flow', () => {
  test('Login should fail for invalid credentials', async ({ flow, log }) => {
    await flow.start();
    log.info('Home page opened for invalid credential test');

    await flow.clickLogin();
    log.info('Navigated to login page');

    await expect(await flow.currentUrl()).toContain('/login');
    log.info('Login URL verified');

    await flow.login('invalid.user@example.com', 'WrongPassword@123');
    log.info('Attempted login with invalid credentials', {
      username: 'invalid.user@example.com',
      password: 'WrongPassword@123'
    });

    await expect(await flow.currentUrl()).toContain('/login');
    await expect(await flow.isSignInButtonVisible()).toBeTruthy();
    log.info('Invalid login rejected and sign in button remains visible');
  });

  test('Login should not proceed when password is empty', async ({ flow, log }) => {
    await flow.start();
    log.info('Home page opened for empty password test');

    await flow.clickLogin();
    log.info('Navigated to login page');

    await expect(await flow.currentUrl()).toContain('/login');
    log.info('Login URL verified');

    await flow.login('valid.user@example.com', '');
    log.info('Attempted login with empty password', {
      username: 'valid.user@example.com',
      password: ''
    });

    await expect(await flow.currentUrl()).toContain('/login');
    await expect(await flow.isSignInButtonVisible()).toBeTruthy();
    log.info('Empty password login rejected and sign in button remains visible');
  });

});
