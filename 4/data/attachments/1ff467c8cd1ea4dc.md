# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: negative-flow.spec.ts >> Vikram (1030) | negative UI paths >> redirects an unauthenticated traveller away from passenger details
- Location: tests\negative-flow.spec.ts:13:7

# Error details

```
Error: expect(locator).toBeVisible() failed

Locator: getByRole('heading', { name: 'Welcome back' })
Expected: visible
Timeout: 5000ms
Error: element(s) not found

Call log:
  - Expect "toBeVisible" with timeout 5000ms
  - waiting for getByRole('heading', { name: 'Welcome back' })

```

```yaml
- banner:
  - link "TripStack":
    - /url: /
  - navigation "Primary":
    - link "Flights":
      - /url: /flights/search
    - link "Buses":
      - /url: /buses/search
    - link "Log in":
      - /url: /login
- main:
  - heading "Sign in to TripStack" [level=1]
  - paragraph: Book flights and buses, pick your seat, get a PNR.
  - text: Email
  - textbox "Email"
  - text: Password
  - textbox "Password"
  - button "Sign in"
  - paragraph:
    - text: Demo login —
    - code: dave@tripstack.test
    - text: /
    - code: Password@123
- contentinfo: © TripStack — a demo travel marketplace for SDET training. Flights · Buses · Seat selection · Secure checkout
```

# Test source

```ts
  1  | import { test, expect } from './fixtures/tripstack.fixture';
  2  | import { SeatSelectionPage } from './pages/seat-selection.page';
  3  | 
  4  | test.describe('Vikram (1030) | negative UI paths', () => {
  5  |   test('rejects invalid login credentials', async ({ page, loginPage }) => {
  6  |     await loginPage.goto();
  7  |     await loginPage.loginWith('invalid@tripstack.test', 'wrong-password');
  8  | 
  9  |     await expect(page).toHaveURL(/\/login/);
  10 |     await expect(page.getByRole('alert')).toHaveText('Invalid email or password.');
  11 |   });
  12 | 
  13 |   test('redirects an unauthenticated traveller away from passenger details', async ({ page }) => {
  14 |     await page.goto('/book/passenger?type=bus&inventory=BUS-HYDBOM-03&seats=S1');
  15 | 
  16 |     await expect(page).toHaveURL(/\/login/);
> 17 |     await expect(page.getByRole('heading', { name: 'Welcome back' })).toBeVisible();
     |                                                                       ^ Error: expect(locator).toBeVisible() failed
  18 |   });
  19 | 
  20 |   test('does not allow a booked seat to be selected', async ({ page }) => {
  21 |     await page.goto('/buses/BUS-HYDBOM-03/seatmap');
  22 |     const bookedSeat = page.getByRole('button', { name: /^Seat .+ booked$/ }).first();
  23 | 
  24 |     await expect(bookedSeat).toHaveAttribute('aria-disabled', 'true');
  25 |     await expect(page.getByRole('button', { name: 'Continue to passenger details' })).toBeDisabled();
  26 |   });
  27 | 
  28 |   test('requires traveller details before continuing to payment', async ({ page, loginPage }) => {
  29 |     test.skip(!process.env.TRIPSTACK_EMAIL || !process.env.TRIPSTACK_PASSWORD,
  30 |       'Set login environment variables to run the passenger-validation test.');
  31 |     await loginPage.goto();
  32 |     await loginPage.login();
  33 |     await page.goto('/buses/BUS-HYDBOM-03/seatmap');
  34 | 
  35 |     const seats = new SeatSelectionPage(page);
  36 |     await seats.selectFirstAvailableSeat();
  37 |     await seats.continueToPassengerDetails();
  38 |     await page.getByRole('button', { name: 'Continue to payment' }).click();
  39 | 
  40 |     await expect(page).toHaveURL(/\/book\/passenger/);
  41 |     await expect.poll(() => page.locator('input[name^="firstName_"]')
  42 |       .evaluate(input => (input as HTMLInputElement).validity.valid)).toBe(false);
  43 |   });
  44 | });
  45 | 
```