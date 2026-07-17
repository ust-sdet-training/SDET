# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: booking-validation.spec.ts >> booking page should validate past date selection
- Location: assessment-testt\ui--automation\tests\booking-validation.spec.ts:12:5

# Error details

```
Test timeout of 120000ms exceeded.
```

```
Error: locator.click: Test timeout of 120000ms exceeded.
Call log:
  - waiting for getByRole('button', { name: '12' }).first()

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
    - heading "Book flights & buses across India" [level=1] [ref=e12]
    - paragraph [ref=e13]: One search, best fares — pick your seat, pay securely, get an instant PNR.
    - tablist "Choose journey" [ref=e15]:
      - tab "Flights" [ref=e16] [cursor=pointer]:
        - img [ref=e17]
        - text: Flights
      - tab "Buses" [ref=e19] [cursor=pointer]:
        - img [ref=e20]
        - text: Buses
  - main [ref=e22]:
    - search "Search journeys" [ref=e24]:
      - generic [ref=e25]:
        - generic [ref=e26]: From
        - combobox "From" [ref=e27]: GOI
      - button "Swap origin and destination" [ref=e28] [cursor=pointer]:
        - img [ref=e29]
      - generic [ref=e31]:
        - generic [ref=e32]: To
        - combobox "To" [ref=e33]: BLR
      - generic [ref=e34]:
        - generic [ref=e35]: Date
        - textbox "Date" [active] [ref=e36]
      - button "Search" [ref=e37] [cursor=pointer]
    - generic "Offers" [ref=e38]:
      - generic [ref=e39]: FLAT 12% off on your first booking
      - generic [ref=e40]: No convenience fee on UPI payments
      - generic [ref=e41]: Free seat selection on select buses
    - heading "Popular routes" [level=2] [ref=e42]
    - generic [ref=e43]:
      - link "Delhi → Bengaluru" [ref=e44] [cursor=pointer]:
        - /url: /buses/results?from=DEL&to=BLR
      - link "Pune → Mumbai" [ref=e45] [cursor=pointer]:
        - /url: /buses/results?from=PUN&to=BOM
      - link "Chennai → Bengaluru" [ref=e46] [cursor=pointer]:
        - /url: /buses/results?from=MAA&to=BLR
      - link "Mumbai → Delhi" [ref=e47] [cursor=pointer]:
        - /url: /buses/results?from=BOM&to=DEL
  - generic "Why TripStack" [ref=e50]:
    - generic [ref=e51]:
      - img [ref=e52]
      - text: Live tracking
    - generic [ref=e54]:
      - img [ref=e55]
      - text: Secure payments
    - generic [ref=e57]:
      - img [ref=e58]
      - text: 24×7 support
  - contentinfo [ref=e60]:
    - generic [ref=e61]:
      - generic [ref=e62]: © TripStack — a demo travel marketplace for SDET training.
      - generic [ref=e63]: Flights · Buses · Seat selection · Secure checkout
```

# Test source

```ts
  1  | import { expect, Locator, Page } from "@playwright/test";
  2  | 
  3  | export class BookingPage {
  4  |   readonly page: Page;
  5  |   readonly busTab: Locator;
  6  |   readonly fromInput: Locator;
  7  |   readonly toInput: Locator;
  8  |   readonly dateInput: Locator;
  9  |   readonly searchButton: Locator;
  10 |   readonly continueButton: Locator;
  11 | 
  12 |   constructor(page: Page) {
  13 |     this.page = page;
  14 |     this.busTab = page.getByRole("tab", { name: "Buses" });
  15 |     this.fromInput = page.getByRole("combobox", { name: "From" });
  16 |     this.toInput = page.getByRole("combobox", { name: "To" });
  17 |     this.dateInput = page.getByRole("textbox", { name: "Date" });
  18 |     this.searchButton = page.getByRole("button", { name: "Search" });
  19 |     this.continueButton = page.locator("#continue-btn");
  20 |   }
  21 | 
  22 |   async chooseBusRoute(from: string, to: string, date: string) {
  23 |     await this.busTab.click();
  24 |     await this.fromInput.click();
  25 |     await this.fromInput.fill(from);
  26 |     await this.page.getByRole("option", { name: `Goa GOI` }).click();
  27 |     await this.toInput.click();
  28 |     await this.page.getByRole("option", { name: `Bengaluru BLR` }).click();
  29 |     await this.dateInput.fill(date);
  30 |     await this.searchButton.click();
  31 |   }
  32 | 
  33 |   // Use the visible datepicker to select a date (simulates real user interaction)
  34 |   async chooseBusRouteWithDatePicker(from: string, to: string, date: string) {
  35 |     await this.busTab.click();
  36 |     await this.fromInput.click();
  37 |     await this.fromInput.fill(from);
  38 |     await this.page.getByRole("option", { name: `Goa GOI` }).click();
  39 |     await this.toInput.click();
  40 |     await this.page.getByRole("option", { name: `Bengaluru BLR` }).click();
  41 | 
  42 |     await this.dateInput.click();
  43 |     const day = String(new Date(date).getDate());
  44 | 
  45 |     // Try to click a calendar cell/button that matches the day number.
  46 |     // This is more like a user selection than programmatic input and should
  47 |     // trigger client-side validation tied to the datepicker.
  48 |     const dayButton = this.page.getByRole("button", { name: day }).first();
> 49 |     await dayButton.click();
     |                     ^ Error: locator.click: Test timeout of 120000ms exceeded.
  50 | 
  51 |     await this.searchButton.click();
  52 |   }
  53 | 
  54 |   async selectBus() {
  55 |     await this.page
  56 |       .getByLabel("Orange Tours")
  57 |       .getByRole("button", { name: "Select Seats" })
  58 |       .click();
  59 |   }
  60 | 
  61 |   async chooseSeat() {
  62 |     const availableSeat = this.page
  63 |       .locator(".seat.available[role='button']")
  64 |       .first();
  65 | 
  66 |     await expect(availableSeat).toBeVisible({ timeout: 10000 });
  67 |     await availableSeat.click();
  68 |     await expect(this.continueButton).toBeEnabled({ timeout: 10000 });
  69 |   }
  70 | 
  71 |   async continueToPassengerDetails() {
  72 |     await this.page
  73 |       .getByRole("button", { name: "Continue to passenger details" })
  74 |       .click();
  75 |   }
  76 | }
  77 | 
```