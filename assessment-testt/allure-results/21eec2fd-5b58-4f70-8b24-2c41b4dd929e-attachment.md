# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: login-negative.spec.ts >> invalid login should stay on the login page
- Location: ui--automation\tests\login-negative.spec.ts:5:5

# Error details

```
Error: expect(locator).toHaveValue(expected) failed

Locator:  getByRole('textbox', { name: 'Email' })
Expected: "invalid.user@test.local"
Received: ""
Timeout:  5000ms

Call log:
  - Expect "toHaveValue" with timeout 5000ms
  - waiting for getByRole('textbox', { name: 'Email' })
    14 × locator resolved to <input id="email" required="" type="email" name="email" data-id="login-email" autocomplete="username"/>
       - unexpected value ""

```

```yaml
- textbox "Email"
```

# Test source

```ts
  1  | import { test, expect } from "./fixtures";
  2  | import { ENV } from "../src/config/env";
  3  | import { LoginPage } from "../src/pages/login.page";
  4  | 
  5  | test("invalid login should stay on the login page", async ({ page }) => {
  6  |   const loginPage = new LoginPage(page);
  7  | 
  8  |   await page.goto(ENV.baseUrl);
  9  |   await page.getByRole("link", { name: "Log in" }).click();
  10 | 
  11 |   await loginPage.login("invalid.user@test.local", "wrong-password");
  12 | 
  13 |   await expect(loginPage.signInButton).toBeVisible({ timeout: 5000 });
> 14 |   await expect(loginPage.emailInput).toHaveValue("invalid.user@test.local");
     |                                      ^ Error: expect(locator).toHaveValue(expected) failed
  15 | });
  16 | 
```