# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: Flight-Booking.spec.ts >> Flight Booking Test
- Location: tests\Flight-Booking.spec.ts:5:5

# Error details

```
Error: expect(received).toContain(expected) // indexOf

Expected substring: "/book/payment"
Received string:    "https://tripstack.doomple.com/book/passenger"
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
          - /url: https://tripstack.doomple.com/book/passenger?type=flight&inventory=FL-BLRMAA-51&seats=3D
        - link "Go to home" [ref=e18] [cursor=pointer]:
          - /url: /
  - contentinfo [ref=e19]:
    - generic [ref=e20]:
      - generic [ref=e21]: © TripStack — a demo travel marketplace for SDET training.
      - generic [ref=e22]: Flights · Buses · Seat selection · Secure checkout
```

# Test source

```ts
  1  | import { Locator, Page, expect } from "@playwright/test";
  2  | 
  3  | export class PaymentDetailsPage {
  4  | 
  5  |     constructor(readonly page: Page) { }
  6  | 
  7  |     private nameOnCardTextBox = (): Locator => this.page.getByRole('textbox', { name: 'Name on card' });
  8  |     private cardNumberTextBox = (): Locator => this.page.getByRole('textbox', { name: 'Card number' });
  9  |     private expiryDateTextBox = (): Locator => this.page.getByRole('textbox', { name: 'Expiry' });
  10 |     private cvvTextBox = (): Locator => this.page.getByRole('textbox', { name: 'CVV' });
  11 |     private payButton = (): Locator => this.page.getByRole('button', { name: 'Pay ₹' });
  12 | 
  13 |     async verifyPaymentDetailsPageLoaded() {
> 14 |         await expect(this.page.url()).toContain('/book/payment');
     |                                       ^ Error: expect(received).toContain(expected) // indexOf
  15 |         await expect(this.nameOnCardTextBox()).toBeVisible();
  16 |     }
  17 | 
  18 |     async fillPaymentDetails(nameOnCard:string,cardNumber:string,expiryDate:string,cvv:string) {
  19 |         await this.nameOnCardTextBox().fill(nameOnCard);
  20 |         await this.cardNumberTextBox().fill(cardNumber);
  21 |         await this.expiryDateTextBox().fill(expiryDate);
  22 |         await this.cvvTextBox().fill(cvv);
  23 |         await this.payButton().click();
  24 |     }
  25 | 
  26 |     
  27 | }
```