# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: booking-flow.spec.ts >> complete bus booking >> books HYD to BOM AC Semi-Sleeper and verifies its PNR in My Trips
- Location: tests\booking-flow.spec.ts:14:7

# Error details

```
Test timeout of 30000ms exceeded.
```

```
Error: expect(locator).toBeVisible() failed

Locator:  getByRole('heading', { name: 'Seat no longer available' })
Expected: visible
Received: undefined

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
    - generic [ref=e12]:
      - generic [ref=e13]: "409"
      - heading "Seat no longer available" [level=1] [ref=e14]
      - alert [ref=e15]: One of those seats was just taken. Please pick another.
      - generic [ref=e16]:
        - link "← Back" [ref=e17] [cursor=pointer]:
          - /url: https://tripstack.doomple.com/book/passenger?type=bus&inventory=BUS-HYDBOM-03&seats=S2&boardingPoint=Hyderabad+ISBT&droppingPoint=Mumbai+Main+Bus+Terminal
        - link "Go to home" [ref=e18] [cursor=pointer]:
          - /url: /
  - contentinfo [ref=e19]:
    - generic [ref=e20]:
      - generic [ref=e21]: © TripStack — a demo travel marketplace for SDET training.
      - generic [ref=e22]: Flights · Buses · Seat selection · Secure checkout
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
  17 |   async submit(): Promise<'payment' | 'seat-conflict'> {
  18 |     await this.page.getByRole('button', { name: 'Continue to payment' }).click();
  19 | 
  20 |     try {
  21 |       await this.page.waitForURL(/\/book\/payment\//, { timeout: 5000 });
  22 |       return 'payment';
  23 |     } catch {
> 24 |       await expect(this.page.getByRole('heading', { name: 'Seat no longer available' })).toBeVisible();
     |                                                                                          ^ Error: expect(locator).toBeVisible() failed
  25 |       return 'seat-conflict';
  26 |     }
  27 |   }
  28 | }
  29 | 
```