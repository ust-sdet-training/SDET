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
    - heading "Secure checkout" [level=1] [ref=e22]
    - paragraph [ref=e23]: Confirm booking 42c27984-54a1-48e7-b94e-e34ab3787bf6 by entering your card details.
    - generic [ref=e24]:
      - generic [ref=e25]:
        - generic [ref=e26]:
          - generic [ref=e27]: FLAT ₹100 OFF · use TRIP100
          - generic [ref=e28]: 10% cashback on UPI
          - generic [ref=e29]: No convenience fee today
        - generic [ref=e30]:
          - heading "Card details" [level=2] [ref=e31]
          - generic [ref=e32]:
            - generic [ref=e33]: Name on card
            - textbox "Name on card" [active] [ref=e34]: Ivan R
          - generic [ref=e35]:
            - generic [ref=e36]: Card number
            - textbox "Card number" [ref=e37]:
              - /placeholder: 1234 5678 9012 3456
          - generic [ref=e38]:
            - generic [ref=e39]:
              - generic [ref=e40]: Expiry
              - textbox "Expiry" [ref=e41]:
                - /placeholder: MM/YY
            - generic [ref=e42]:
              - generic [ref=e43]: CVV
              - textbox "CVV" [ref=e44]
          - button "Pay ₹4578.40" [ref=e45] [cursor=pointer]
          - paragraph [ref=e46]:
            - img [ref=e47]
            - text: This is a training sandbox — no real card is charged.
      - complementary [ref=e49]:
        - generic [ref=e50]:
          - paragraph [ref=e51]: Fare breakup
          - generic [ref=e52]:
            - generic [ref=e53]: Journey
            - generic [ref=e54]: Flight
          - generic [ref=e55]:
            - generic [ref=e56]: Seats
            - generic [ref=e57]: 3D
          - generic [ref=e58]:
            - generic [ref=e59]: Base fare (1 × ₹4578.40)
            - generic [ref=e60]: ₹4578.40
          - generic [ref=e61]:
            - generic [ref=e62]: Taxes & fees
            - generic [ref=e63]: Included
          - generic [ref=e64]:
            - generic [ref=e65]: Total payable
            - generic [ref=e66]: ₹4578.40
  - contentinfo [ref=e67]:
    - generic [ref=e68]:
      - generic [ref=e69]: © TripStack — a demo travel marketplace for SDET training.
      - generic [ref=e70]: Flights · Buses · Seat selection · Secure checkout
```