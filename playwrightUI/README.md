# TripStack UI automation

Playwright Page Object Model suite for Vikram (employee `1030`): HYD → BOM, AC Semi-Sleeper, today + 14 days.

## Setup

Copy `.env.example` to `.env` and replace the placeholder values locally. `.env` is ignored; in CI, add the same names as repository secrets.

```powershell
$env:TRIPSTACK_EMAIL = '...'
$env:TRIPSTACK_PASSWORD = '...'
npx playwright test
```

The unauthenticated search tests can run without credentials. Authentication tests are skipped until both variables are set.

`booking-flow.spec.ts` covers Login → Landing → Buses → Search → Results → Seat Selection → Passenger Details → Payment → Confirmation/PNR → My Trips. It is opt-in because it creates a real booking under employee `1030`. Set `TRIPSTACK_RUN_BOOKING=true` and provide the extra variables in `.env.example` to run it. Payment-flow artifacts are disabled so sensitive values are not retained in screenshots, videos, or traces.

`negative-flow.spec.ts` covers invalid credentials, protected checkout access, booked seats, and mandatory passenger details. `payment-failure.spec.ts` is the assigned Payment 500 resilience test; enable it only after the director activates the fault with `TRIPSTACK_RUN_PAYMENT_FAILURE=true`.

Locators use accessible roles, labels, and stable data attributes rather than CSS classes, so the assigned class-rename DOM change does not break the suite.
