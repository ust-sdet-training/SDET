# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: booking\bookingFlow.spec.ts >> TripStack Bus Booking Flow
- Location: tests\booking\bookingFlow.spec.ts:7:5

# Error details

```
Error: locator.click: Error: strict mode violation: getByRole('button', { name: /continue|proceed|book|checkout/i }) resolved to 3 elements:
    1) <div role="button" data-seat="L4" data-deck="lower" data-kind="sleeper" data-state="booked" aria-disabled="true" aria-label="Seat L4 booked" class="seat sleeper berth booked is-booked">…</div> aka getByRole('button', { name: 'Seat L4 booked' })
    2) <div role="button" data-seat="L8" data-deck="lower" data-kind="sleeper" data-state="booked" aria-disabled="true" aria-label="Seat L8 booked" class="seat sleeper berth booked is-booked">…</div> aka getByRole('button', { name: 'Seat L8 booked' })
    3) <button type="submit" id="continue-btn" class="btn btn-cta">Continue to passenger details</button> aka getByRole('button', { name: 'Continue to passenger details' })

Call log:
  - waiting for getByRole('button', { name: /continue|proceed|book|checkout/i })

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
    - heading "Select seats — NueGo Electric" [level=1] [ref=e22]
    - paragraph [ref=e23]:
      - text: IXC → BLR · 10:52 → 19:00 · A/C Sleeper (2+1) ·
      - generic [ref=e24]:
        - img [ref=e25]
        - text: "4.4"
    - list "Seat legend" [ref=e27]:
      - listitem [ref=e28]: Available
      - listitem [ref=e30]: Booked
      - listitem [ref=e32]: Ladies
      - listitem [ref=e34]: Selected
    - generic [ref=e36]:
      - generic [ref=e37]:
        - tablist "Choose deck" [ref=e38]:
          - tab "Lower deck" [selected] [ref=e39] [cursor=pointer]
          - tab "Upper deck" [ref=e40] [cursor=pointer]
        - generic [ref=e41]:
          - generic "Driver" [ref=e42]:
            - img [ref=e43]
          - tabpanel "lower deck" [ref=e45]:
            - generic [ref=e46]: Lower deck
            - generic [ref=e47]:
              - button "Seat L1 ladies" [ref=e48] [cursor=pointer]:
                - generic [ref=e49]: L1
              - button "Seat L2 ladies" [ref=e50] [cursor=pointer]:
                - generic [ref=e51]: L2
              - button "Seat L3 available" [pressed] [ref=e52] [cursor=pointer]:
                - generic [ref=e53]: L3
              - button "Seat L4 booked" [disabled] [ref=e54]:
                - generic [ref=e55]: L4
              - button "Seat L5 available" [ref=e56] [cursor=pointer]:
                - generic [ref=e57]: L5
              - button "Seat L6 available" [ref=e58] [cursor=pointer]:
                - generic [ref=e59]: L6
              - button "Seat L7 available" [ref=e60] [cursor=pointer]:
                - generic [ref=e61]: L7
              - button "Seat L8 booked" [disabled] [ref=e62]:
                - generic [ref=e63]: L8
              - button "Seat L9 available" [ref=e64] [cursor=pointer]:
                - generic [ref=e65]: L9
              - button "Seat L10 available" [ref=e66] [cursor=pointer]:
                - generic [ref=e67]: L10
      - generic [ref=e68]:
        - heading "Boarding points" [level=2] [ref=e69]
        - radiogroup "Boarding points" [ref=e70]:
          - generic [ref=e71] [cursor=pointer]:
            - generic [ref=e72]: Chandigarh Airport Road
            - generic [ref=e73]: 10:32
            - radio "Chandigarh Airport Road 10:32" [checked] [ref=e74]
          - generic [ref=e75] [cursor=pointer]:
            - generic [ref=e76]: Chandigarh Satellite Station
            - generic [ref=e77]: 10:42
            - radio "Chandigarh Satellite Station 10:42" [ref=e78]
          - generic [ref=e79] [cursor=pointer]:
            - generic [ref=e80]: Chandigarh City Junction
            - generic [ref=e81]: 10:52
            - radio "Chandigarh City Junction 10:52" [ref=e82]
          - generic [ref=e83] [cursor=pointer]:
            - generic [ref=e84]: Chandigarh Central Bus Stand
            - generic [ref=e85]: 11:02
            - radio "Chandigarh Central Bus Stand 11:02" [ref=e86]
        - heading "Dropping points" [level=2] [ref=e87]
        - radiogroup "Dropping points" [ref=e88]:
          - generic [ref=e89] [cursor=pointer]:
            - generic [ref=e90]: Bengaluru Railway Station
            - generic [ref=e91]: 19:05
            - radio "Bengaluru Railway Station 19:05" [checked] [active] [ref=e92]
          - generic [ref=e93] [cursor=pointer]:
            - generic [ref=e94]: Bengaluru Tech Park Gate
            - generic [ref=e95]: 19:20
            - radio "Bengaluru Tech Park Gate 19:20" [ref=e96]
          - generic [ref=e97] [cursor=pointer]:
            - generic [ref=e98]: Bengaluru Main Bus Terminal
            - generic [ref=e99]: 19:35
            - radio "Bengaluru Main Bus Terminal 19:35" [ref=e100]
  - generic [ref=e102]:
    - generic [ref=e103]:
      - strong [ref=e104]: "1"
      - text: seat(s) selected — ₹1556.10
    - button "Continue to passenger details" [ref=e106] [cursor=pointer]
  - contentinfo [ref=e107]:
    - generic [ref=e108]:
      - generic [ref=e109]: © TripStack — a demo travel marketplace for SDET training.
      - generic [ref=e110]: Flights · Buses · Seat selection · Secure checkout
```

# Test source

```ts
  1  | import { Page } from '@playwright/test';
  2  | 
  3  | export class SeatMapPage {
  4  | 
  5  |     constructor(
  6  |         private page: Page
  7  |     ) {}
  8  | 
  9  |     async selectSeat(
  10 |         preferredSeat: string = 'L3'
  11 |     ) {
  12 | 
  13 |         const preferredSeatLocator =
  14 |             this.page.getByRole(
  15 |                 'button',
  16 |                 {
  17 |                     name:
  18 |                         `Seat ${preferredSeat} available`
  19 |                 }
  20 |             );
  21 | 
  22 |         if (
  23 |             await preferredSeatLocator.count() > 0
  24 |         ) {
  25 | 
  26 |             await preferredSeatLocator.click();
  27 | 
  28 |             console.log(
  29 |                 `Selected preferred seat: ${preferredSeat}`
  30 |             );
  31 | 
  32 |             return;
  33 |         }
  34 | 
  35 |         const fallbackSeat =
  36 |             this.page.locator(
  37 |                 'button[aria-label*="available"]'
  38 |             ).first();
  39 | 
  40 |         await fallbackSeat.waitFor({
  41 |             state: 'visible',
  42 |             timeout: 10000
  43 |         });
  44 | 
  45 |         await fallbackSeat.click();
  46 | 
  47 |         console.log(
  48 |             `Preferred seat ${preferredSeat} unavailable. Selected first available seat instead.`
  49 |         );
  50 |     }
  51 | 
  52 |     async selectBoardingPoint() {
  53 | 
  54 |         const boardingPoint =
  55 |             this.page
  56 |                 .getByText(
  57 |                     'Bengaluru Railway Station'
  58 |                 )
  59 |                 .first();
  60 | 
  61 |         if (
  62 |             await boardingPoint.count() > 0
  63 |         ) {
  64 |             await boardingPoint.click();
  65 |         }
  66 |     }
  67 | 
  68 |     async continueBooking() {
  69 | 
  70 |         const continueButton =
  71 |             this.page.getByRole(
  72 |                 'button',
  73 |                 {
  74 |                     name:
  75 |                         /continue|proceed|book|checkout/i
  76 |                 }
  77 |             );
  78 | 
  79 |         if (
  80 |             await continueButton.count() > 0
  81 |         ) {
> 82 |             await continueButton.click();
     |                                  ^ Error: locator.click: Error: strict mode violation: getByRole('button', { name: /continue|proceed|book|checkout/i }) resolved to 3 elements:
  83 |         }
  84 |     }
  85 | }
```