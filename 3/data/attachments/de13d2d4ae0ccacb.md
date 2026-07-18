# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: negative-flow.spec.ts >> negative UI paths >> requires traveller details before continuing to payment
- Location: tests\negative-flow.spec.ts:28:7

# Error details

```
TimeoutError: locator.click: Timeout 15000ms exceeded.
Call log:
  - waiting for getByRole('button', { name: /seat/i }).filter({ hasNotText: /booked|unavailable|reserved/i }).first()
    - locator resolved to <div role="button" data-seat="S1" data-deck="lower" data-kind="seater" data-state="booked" aria-disabled="true" aria-label="Seat S1 booked" class="seat booked is-booked">…</div>
  - attempting click action
    2 × waiting for element to be visible, enabled and stable
      - element is not enabled
    - retrying click action
    - waiting 20ms
    2 × waiting for element to be visible, enabled and stable
      - element is not enabled
    - retrying click action
      - waiting 100ms
    29 × waiting for element to be visible, enabled and stable
       - element is not enabled
     - retrying click action
       - waiting 500ms

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
    - heading "Select seats — KPN Travels" [level=1] [ref=e22]
    - paragraph [ref=e23]:
      - text: HYD → BOM · 21:39 → 05:00 · A/C Semi-Sleeper (2+2) ·
      - generic [ref=e24]:
        - img [ref=e25]
        - text: "4.7"
    - list "Seat legend" [ref=e27]:
      - listitem [ref=e28]: Available
      - listitem [ref=e30]: Booked
      - listitem [ref=e32]: Ladies
      - listitem [ref=e34]: Selected
    - generic [ref=e36]:
      - generic [ref=e38]:
        - generic "Driver" [ref=e39]:
          - img [ref=e40]
        - tabpanel "lower deck" [ref=e42]:
          - generic [ref=e43]: Lower deck
          - generic [ref=e44]:
            - button "Seat S1 booked" [disabled] [ref=e45]:
              - generic [ref=e46]: S1
            - button "Seat S2 available" [ref=e47] [cursor=pointer]:
              - generic [ref=e48]: S2
            - button "Seat S3 booked" [disabled] [ref=e49]:
              - generic [ref=e50]: S3
            - button "Seat S4 available" [ref=e51] [cursor=pointer]:
              - generic [ref=e52]: S4
            - button "Seat S5 available" [ref=e53] [cursor=pointer]:
              - generic [ref=e54]: S5
            - button "Seat S6 booked" [disabled] [ref=e55]:
              - generic [ref=e56]: S6
            - button "Seat S7 ladies" [ref=e57] [cursor=pointer]:
              - generic [ref=e58]: S7
            - button "Seat S8 available" [ref=e59] [cursor=pointer]:
              - generic [ref=e60]: S8
            - button "Seat S9 booked" [disabled] [ref=e61]:
              - generic [ref=e62]: S9
            - button "Seat S10 available" [ref=e63] [cursor=pointer]:
              - generic [ref=e64]: S10
            - button "Seat S11 available" [ref=e65] [cursor=pointer]:
              - generic [ref=e66]: S11
            - button "Seat S12 booked" [disabled] [ref=e67]:
              - generic [ref=e68]: S12
            - button "Seat S13 available" [ref=e69] [cursor=pointer]:
              - generic [ref=e70]: S13
            - button "Seat S14 ladies" [ref=e71] [cursor=pointer]:
              - generic [ref=e72]: S14
            - button "Seat S15 booked" [disabled] [ref=e73]:
              - generic [ref=e74]: S15
            - button "Seat S16 available" [ref=e75] [cursor=pointer]:
              - generic [ref=e76]: S16
            - button "Seat S17 available" [ref=e77] [cursor=pointer]:
              - generic [ref=e78]: S17
            - button "Seat S18 booked" [disabled] [ref=e79]:
              - generic [ref=e80]: S18
            - button "Seat S19 available" [ref=e81] [cursor=pointer]:
              - generic [ref=e82]: S19
            - button "Seat S20 available" [ref=e83] [cursor=pointer]:
              - generic [ref=e84]: S20
            - button "Seat S21 booked" [disabled] [ref=e85]:
              - generic [ref=e86]: S21
            - button "Seat S22 available" [ref=e87] [cursor=pointer]:
              - generic [ref=e88]: S22
            - button "Seat S23 available" [ref=e89] [cursor=pointer]:
              - generic [ref=e90]: S23
            - button "Seat S24 booked" [disabled] [ref=e91]:
              - generic [ref=e92]: S24
            - button "Seat S25 available" [ref=e93] [cursor=pointer]:
              - generic [ref=e94]: S25
            - button "Seat S26 available" [ref=e95] [cursor=pointer]:
              - generic [ref=e96]: S26
            - button "Seat S27 booked" [disabled] [ref=e97]:
              - generic [ref=e98]: S27
            - button "Seat S28 ladies" [ref=e99] [cursor=pointer]:
              - generic [ref=e100]: S28
            - button "Seat S29 available" [ref=e101] [cursor=pointer]:
              - generic [ref=e102]: S29
            - button "Seat S30 booked" [disabled] [ref=e103]:
              - generic [ref=e104]: S30
      - generic [ref=e105]:
        - heading "Boarding points" [level=2] [ref=e106]
        - radiogroup "Boarding points" [ref=e107]:
          - generic [ref=e108] [cursor=pointer]:
            - generic [ref=e109]: Hyderabad ISBT
            - generic [ref=e110]: 21:19
            - radio "Hyderabad ISBT 21:19" [checked] [ref=e111]
          - generic [ref=e112] [cursor=pointer]:
            - generic [ref=e113]: Hyderabad Airport Road
            - generic [ref=e114]: 21:29
            - radio "Hyderabad Airport Road 21:29" [ref=e115]
          - generic [ref=e116] [cursor=pointer]:
            - generic [ref=e117]: Hyderabad Satellite Station
            - generic [ref=e118]: 21:39
            - radio "Hyderabad Satellite Station 21:39" [ref=e119]
        - heading "Dropping points" [level=2] [ref=e120]
        - radiogroup "Dropping points" [ref=e121]:
          - generic [ref=e122] [cursor=pointer]:
            - generic [ref=e123]: Mumbai Main Bus Terminal
            - generic [ref=e124]: 05:05
            - radio "Mumbai Main Bus Terminal 05:05" [checked] [ref=e125]
          - generic [ref=e126] [cursor=pointer]:
            - generic [ref=e127]: Mumbai Highway Toll Plaza
            - generic [ref=e128]: 05:20
            - radio "Mumbai Highway Toll Plaza 05:20" [ref=e129]
          - generic [ref=e130] [cursor=pointer]:
            - generic [ref=e131]: Mumbai Railway Station
            - generic [ref=e132]: 05:35
            - radio "Mumbai Railway Station 05:35" [ref=e133]
  - generic [ref=e135]:
    - generic [ref=e136]:
      - strong [ref=e137]: "0"
      - text: seat(s) selected — ₹0.00
    - button "Continue to passenger details" [disabled] [ref=e139]
  - contentinfo [ref=e140]:
    - generic [ref=e141]:
      - generic [ref=e142]: © TripStack — a demo travel marketplace for SDET training.
      - generic [ref=e143]: Flights · Buses · Seat selection · Secure checkout
```

# Test source

```ts
  1  | import { expect, type Page } from '@playwright/test';
  2  | 
  3  | export class SeatSelectionPage {
  4  |   constructor(private readonly page: Page) {}
  5  | 
  6  |   async selectAvailableSeat(index = 0): Promise<void> {
  7  |     const candidates = this.page
  8  |       .getByRole('button', { name: /seat/i })
  9  |       .filter({ hasNotText: /booked|unavailable|reserved/i });
  10 | 
  11 |     const seat = candidates.nth(index);
  12 |     await expect(seat).toBeVisible({ timeout: 15_000 });
> 13 |     await seat.click();
     |                ^ TimeoutError: locator.click: Timeout 15000ms exceeded.
  14 |     await expect(seat).toHaveAttribute('aria-pressed', 'true');
  15 |   }
  16 | 
  17 |   async continueToPassengerDetails(): Promise<void> {
  18 |     await this.page.getByRole('button', { name: 'Continue to passenger details' }).click();
  19 |     await expect(this.page).toHaveURL(/\/book\/passenger/);
  20 |   }
  21 | }
  22 | 
```