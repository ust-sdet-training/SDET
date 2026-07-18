import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { user } from '../fixtures/test-fixtures';

test('Login', async ({ page }) => {
  const login=new LoginPage(page);

    await login.goto();
    await login.login(user.username,user.password);
    
});

