# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: booking-flow.spec.ts >> Vikram (1030) | complete bus booking >> books HYD to BOM AC Semi-Sleeper and verifies its PNR in My Trips
- Location: tests\booking-flow.spec.ts:17:7

# Error details

```
Error: expect(page).toHaveURL(expected) failed

Expected pattern: /\/book\/payment\//
Received string:  "https://tripstack.doomple.com/book/passenger"
Timeout: 5000ms

Call log:
  - Expect "toHaveURL" with timeout 5000ms
    13 × unexpected value "https://tripstack.doomple.com/book/passenger"

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
    - link "My Trips":
      - /url: /my-trips
    - link "Log out":
      - /url: /logout
- main:
  - text: "409"
  - heading "Seat no longer available" [level=1]
  - alert: One of those seats was just taken. Please pick another.
  - link "← Back":
    - /url: https://tripstack.doomple.com/book/passenger?type=bus&inventory=BUS-HYDBOM-03&seats=S1&boardingPoint=Hyderabad+ISBT&droppingPoint=Mumbai+Main+Bus+Terminal
  - link "Go to home":
    - /url: /
- contentinfo: © TripStack — a demo travel marketplace for SDET training. Flights · Buses · Seat selection · Secure checkout
```

# Test source

```ts
  1  | import { expect, type Page } from '@playwright/test';
  2  | import { env } from '../support/env';
  3  | 
  4  | export class PassengerDetailsPage {
  5  |   constructor(private readonly page: Page) {}
  6  | 
  7  |   async enterDetails(): Promise<void> {
  8  |     const passenger = env.passenger();
  9  |     await expect(this.page.getByRole('heading', { name: "Who's travelling?" })).toBeVisible();
  10 |     await this.page.locator('input[name^="firstName_"]').fill(passenger.firstName);
  11 |     await this.page.locator('input[name^="lastName_"]').fill(passenger.lastName);
  12 |     await this.page.locator('input[name^="passengerAge_"]').fill(passenger.age);
  13 |     await this.page.locator('input[name="email"]').fill(env.credentials().email);
  14 |     await this.page.locator('input[name="phone"]').fill(passenger.phone);
  15 |   }
  16 | 
  17 |   async continueToPayment(): Promise<void> {
  18 |     await this.page.getByRole('button', { name: 'Continue to payment' }).click();
> 19 |     await expect(this.page).toHaveURL(/\/book\/payment\//);
     |                             ^ Error: expect(page).toHaveURL(expected) failed
  20 |   }
  21 | }
  22 | 
```