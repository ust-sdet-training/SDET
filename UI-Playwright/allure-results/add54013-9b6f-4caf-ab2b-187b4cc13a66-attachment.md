# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: example.spec.ts >> test
- Location: tests\example.spec.ts:5:5

# Error details

```
Error: locator.click: Test ended.
Call log:
  - waiting for getByRole('link', { name: 'Log in' })

```

# Test source

```ts
  1  | import { test, expect } from '@playwright/test';
  2  | import { Logger } from '../utils/Logger';
  3  | import { info } from 'winston';
  4  | 
  5  | test('test', async ({ page }) => {
  6  |   await page.goto('/');
> 7  |   await page.getByRole('link', { name: 'Log in' }).click();
     |                                                    ^ Error: locator.click: Test ended.
  8  |   await page.getByRole('textbox', { name: 'Email' }).fill('ivan@tripstack.test');
  9  |   await page.getByRole('textbox', { name: 'Password' }).fill('Password@123');
  10 |   await page.getByRole('button', { name: 'Sign in' }).click();
  11 |   await page.getByRole('combobox', { name: 'From' }).click();
  12 |   await page.getByRole('combobox', { name: 'From' }).fill('Bengaluru (BLR) ');
  13 |   await page.getByRole('option', { name: 'Bengaluru BLR' }).click();
  14 |   await page.getByRole('combobox', { name: 'To' }).click();
  15 |   await page.getByRole('combobox', { name: 'To' }).fill('Chennai (MAA)');
  16 |   await page.getByRole('option', { name: 'Chennai MAA' }).click();
  17 |   await page.getByRole('textbox', { name: 'Date' }).fill('2026-07-26');
  18 |   await page.getByRole('button', { name: 'Search' }).click();
  19 |   await page.getByRole('button', { name: 'Price' }).click();
  20 |   await page.getByLabel('IndiGo 6E-494').getByRole('button', { name: 'Book' }).click();
  21 |   await page.getByLabel('Seat 3D, aisle, available').click();
  22 |   await page.getByRole('button', { name: 'Continue to passenger details' }).click();
  23 |   await page.getByRole('textbox', { name: 'First name (seat 3D)' }).fill('Ivan');
  24 |   await page.getByRole('textbox', { name: 'Last name (seat 3D)' }).fill('Das');
  25 |   await page.getByRole('spinbutton', { name: 'Age (seat 3D)' }).fill('23');
  26 |   await page.getByLabel('Gender (seat 3D)').selectOption('male');
  27 |   await page.getByRole('textbox', { name: 'Email' }).fill('ivan@tripstack.test');
  28 |   await page.getByRole('textbox', { name: 'Phone number' }).fill('9876543210');
  29 |   await page.getByRole('button', { name: 'Continue to payment' }).click();
  30 |   await page.getByRole('textbox', { name: 'Name on card' }).fill('Ivan R');
  31 |   await page.getByRole('textbox', { name: 'Card number' }).fill('1234567856781234');
  32 |   await page.getByRole('textbox', { name: 'Expiry' }).fill('21/32');
  33 |   await page.getByRole('textbox', { name: 'CVV' }).fill('599');
  34 |   await page.getByRole('button', { name: 'Pay ₹' }).click();
  35 |   await expect(page.getByText('CONFIRMED', { exact: true })).toBeVisible();
  36 |   await expect(page.getByText('Your booking is confirmed. A')).toBeVisible();
  37 |   await page.getByRole('button', { name: 'View my trips' }).click();
  38 | });
  39 | 
  40 | 
```