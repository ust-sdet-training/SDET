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
Error: locator.click: Test timeout of 30000ms exceeded.
Call log:
  - waiting for locator('[role="listbox"]:not([hidden])').getByRole('option', { name: /\bHYD\b/ })

```

# Page snapshot

```yaml
- generic [ref=e1]:
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
  - generic [ref=e11]:
    - heading "Book bus tickets across India" [level=1] [ref=e12]
    - paragraph [ref=e13]: Sleeper & seater decks · pick your berth · instant PNR on confirm.
    - generic [ref=e15]:
      - link "Flights" [ref=e16] [cursor=pointer]:
        - /url: /flights/search
        - img [ref=e17]
        - text: Flights
      - link "Buses" [ref=e19] [cursor=pointer]:
        - /url: /buses/search
        - img [ref=e20]
        - text: Buses
    - generic [ref=e22]:
      - generic [ref=e23]:
        - img [ref=e24]
        - text: Live bus tracking
      - generic [ref=e26]:
        - img [ref=e27]
        - text: Secure payments
      - generic [ref=e29]:
        - img [ref=e30]
        - text: 24×7 support
  - main [ref=e32]:
    - generic [ref=e33]:
      - list "Booking progress" [ref=e34]:
        - listitem [ref=e35]:
          - generic [ref=e36]: "1"
          - text: Search
        - listitem [ref=e37]:
          - generic [ref=e38]: "2"
          - text: Seats
        - listitem [ref=e39]:
          - generic [ref=e40]: "3"
          - text: Passenger
        - listitem [ref=e41]:
          - generic [ref=e42]: "4"
          - text: Payment
        - listitem [ref=e43]:
          - generic [ref=e44]: "5"
          - text: Ticket
      - generic [ref=e45]:
        - generic [ref=e46]: FLAT 12% off on your first bus booking
        - generic [ref=e47]: No convenience fee on UPI
        - generic [ref=e48]: Live tracking on select operators
      - search "Bus search" [ref=e49]:
        - generic [ref=e50]:
          - generic [ref=e51]:
            - generic [ref=e52]: From
            - combobox "From" [active] [ref=e53]: HYD
          - button "Swap origin and destination" [ref=e54] [cursor=pointer]:
            - img [ref=e55]
          - generic [ref=e57]:
            - generic [ref=e58]: To
            - combobox "To" [ref=e59]
          - generic [ref=e60]:
            - generic [ref=e61]: Date of journey
            - textbox "Date of journey" [ref=e62]
        - button "Search buses" [ref=e63] [cursor=pointer]
  - contentinfo [ref=e64]:
    - generic [ref=e65]:
      - generic [ref=e66]: © TripStack — a demo travel marketplace for SDET training.
      - generic [ref=e67]: Flights · Buses · Seat selection · Secure checkout
```

# Test source

```ts
  1  | import { expect, type Locator, type Page } from '@playwright/test';
  2  | 
  3  | export class BusSearchPage {
  4  |   readonly searchForm: Locator;
  5  |   readonly from: Locator;
  6  |   readonly to: Locator;
  7  |   readonly date: Locator;
  8  |   readonly search: Locator;
  9  | 
  10 |   constructor(private readonly page: Page) {
  11 |     this.searchForm = page.getByRole('search', { name: 'Bus search' });
  12 |     this.from = page.getByRole('combobox', { name: 'From', exact: true });
  13 |     this.to = page.getByRole('combobox', { name: 'To', exact: true });
  14 |     this.date = page.getByLabel('Date of journey');
  15 |     this.search = page.getByRole('button', { name: 'Search buses' });
  16 |   }
  17 | 
  18 |   async goto(): Promise<void> {
  19 |     await this.page.goto('/buses/search');
  20 |     await expect(this.searchForm).toBeVisible();
  21 |   }
  22 | 
  23 |   async chooseCity(input: Locator, code: string): Promise<void> {
  24 |     await input.fill(code);
  25 |     await this.page
  26 |       .locator('[role="listbox"]:not([hidden])')
  27 |       .getByRole('option', { name: new RegExp(`\\b${code}\\b`) })
> 28 |       .click();
     |        ^ Error: locator.click: Test timeout of 30000ms exceeded.
  29 |   }
  30 | 
  31 |   async searchRoute(from: string, to: string, journeyDate: string): Promise<void> {
  32 |     await this.chooseCity(this.from, from);
  33 |     await this.chooseCity(this.to, to);
  34 |     await this.date.fill(journeyDate);
  35 |     await this.search.click();
  36 |   }
  37 | }
  38 | 
```