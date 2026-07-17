# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: Flight-Booking.spec.ts >> Flight Booking Test
- Location: tests\Flight-Booking.spec.ts:5:5

# Error details

```
Error: expect(locator).toBeVisible() failed

Locator: getByRole('link', { name: 'Trip Stack' })
Expected: visible
Timeout: 5000ms
Error: element(s) not found

Call log:
  - Expect "toBeVisible" with timeout 5000ms
  - waiting for getByRole('link', { name: 'Trip Stack' })

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
- heading "Book flights & buses across India" [level=1]
- paragraph: One search, best fares — pick your seat, pay securely, get an instant PNR.
- tablist "Choose journey":
  - tab "Flights":
    - img
    - text: Flights
  - tab "Buses":
    - img
    - text: Buses
- main:
  - search "Search journeys":
    - text: From
    - combobox "From"
    - button "Swap origin and destination":
      - img
    - text: To
    - combobox "To"
    - text: Date
    - textbox "Date"
    - button "Search"
  - text: FLAT 12% off on your first booking No convenience fee on UPI payments Free seat selection on select buses
  - heading "Popular routes" [level=2]
  - link "Delhi → Mumbai":
    - /url: /flights/results?from=DEL&to=BOM&cls=ECONOMY&pax=1
  - link "Bengaluru → Hyderabad":
    - /url: /flights/results?from=BLR&to=HYD&cls=ECONOMY&pax=1
  - link "Kolkata → Delhi":
    - /url: /flights/results?from=CCU&to=DEL&cls=ECONOMY&pax=1
  - link "Delhi → Bengaluru":
    - /url: /flights/results?from=DEL&to=BLR&cls=ECONOMY&pax=1
- img
- text: Live tracking
- img
- text: Secure payments
- img
- text: 24×7 support
- contentinfo: © TripStack — a demo travel marketplace for SDET training. Flights · Buses · Seat selection · Secure checkout
```

# Test source

```ts
  1  | import { Locator, Page, expect } from "@playwright/test";
  2  | 
  3  | export class HomePage {
  4  | 
  5  |     constructor(readonly page: Page) { }
  6  | 
  7  |     private tripStackLogoLink = () : Locator => this.page.getByRole('link',{name:'Trip Stack'});
  8  |     private tripStackHeader = () : Locator => this.page.getByRole('heading',{name:'Book flights & buses across India'});
  9  |     
  10 |     async open(){
  11 |         await this.page.goto('/');
  12 |     }
  13 | 
  14 |     async verifyHomePageLoaded() {
> 15 |         await expect(this.tripStackLogoLink()).toBeVisible();
     |                                                ^ Error: expect(locator).toBeVisible() failed
  16 |         await expect(this.tripStackHeader()).toBeVisible()
  17 |     }
  18 | 
  19 | }
```