# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: example.spec.ts >> test
- Location: tests\example.spec.ts:5:5

# Error details

```
Test timeout of 30000ms exceeded.
```

```
Error: locator.fill: Test timeout of 30000ms exceeded.
Call log:
  - waiting for getByRole('textbox', { name: 'Name on card' })

```

# Page snapshot

```yaml
- generic [active] [ref=e1]:
  - banner [ref=e2]:
    - generic [ref=e3]:
      - link "TripStack" [ref=e4] [cursor=pointer]:
        - /url: /
      - navigation "Primary" [ref=e5]:
        - link "Flights" [ref=e6] [cursor=pointer]:
          - /url: /flights/search
        - link "Buses" [ref=e7] [cursor=pointer]:
          - /url: /buses/search
        - link "My Trips" [ref=e8] [cursor=pointer]:
          - /url: /my-trips
        - link "Log out" [ref=e9] [cursor=pointer]:
          - /url: /logout
  - main [ref=e10]:
    - list "Booking progress" [ref=e11]:
      - listitem [ref=e12]:
        - generic [ref=e13]: "1"
        - text: Search
      - listitem [ref=e14]:
        - generic [ref=e15]: "2"
        - text: Seats
      - listitem [ref=e16]:
        - generic [ref=e17]: "3"
        - text: Passenger
      - listitem [ref=e18]:
        - generic [ref=e19]: "4"
        - text: Payment
      - listitem [ref=e20]:
        - generic [ref=e21]: "5"
        - text: Ticket
    - heading "Who's travelling?" [level=1] [ref=e22]
    - paragraph [ref=e23]: Add traveller details for each seat, then review your fare before paying.
    - generic [ref=e24]:
      - generic [ref=e25]:
        - generic [ref=e26]:
          - generic [ref=e27]:
            - heading "Traveller 1" [level=2] [ref=e28]
            - generic [ref=e29]: Seat 3D
          - generic [ref=e30]:
            - generic [ref=e31]:
              - generic [ref=e32]: First name (seat 3D)
              - textbox "First name (seat 3D)" [ref=e33]
            - generic [ref=e34]:
              - generic [ref=e35]: Last name (seat 3D)
              - textbox "Last name (seat 3D)" [ref=e36]
          - generic [ref=e37]:
            - generic [ref=e38]:
              - generic [ref=e39]: Age (seat 3D)
              - spinbutton "Age (seat 3D)" [ref=e40]
            - generic [ref=e41]:
              - generic [ref=e42]: Gender (seat 3D)
              - combobox "Gender (seat 3D)" [ref=e43]:
                - option "Female" [selected]
                - option "Male"
                - option "Other"
        - generic [ref=e44]:
          - heading "Contact details" [level=2] [ref=e45]
          - paragraph [ref=e46]: We'll send the ticket and any updates here.
          - generic [ref=e47]:
            - generic [ref=e48]:
              - generic [ref=e49]: Email
              - textbox "Email" [ref=e50]
            - generic [ref=e51]:
              - generic [ref=e52]: Phone number
              - textbox "Phone number" [ref=e53]
        - button "Continue to payment" [ref=e54] [cursor=pointer]
      - complementary [ref=e55]:
        - generic [ref=e56]:
          - paragraph [ref=e57]: Trip summary
          - generic [ref=e58]:
            - generic [ref=e59]: Journey
            - generic [ref=e60]: Flight
          - generic [ref=e61]:
            - generic [ref=e62]: Inventory
            - generic [ref=e63]: FL-BLRMAA-51
          - generic [ref=e64]:
            - generic [ref=e65]: Seats
            - generic [ref=e66]: 3D
          - generic [ref=e67]:
            - generic [ref=e68]: Travellers
            - generic [ref=e69]: "1"
        - paragraph [ref=e71]:
          - img [ref=e72]
          - text: Safe & secure checkout
  - contentinfo [ref=e74]:
    - generic [ref=e75]:
      - generic [ref=e76]: © TripStack — a demo travel marketplace for SDET training.
      - generic [ref=e77]: Flights · Buses · Seat selection · Secure checkout
```

# Test source

```ts
  1  | import { test, expect } from '@playwright/test';
  2  | import { Logger } from '../utils/Logger';
  3  | import { info } from 'winston';
  4  | 
  5  | test('test', async ({ page }) => {
  6  |   await page.goto('/');
  7  |   await page.getByRole('link', { name: 'Log in' }).click();
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
> 30 |   await page.getByRole('textbox', { name: 'Name on card' }).fill('Ivan R');
     |                                                             ^ Error: locator.fill: Test timeout of 30000ms exceeded.
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