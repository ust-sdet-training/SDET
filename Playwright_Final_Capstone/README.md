# Playwright Flight Booking Framework

A TypeScript Playwright framework that demonstrates Page Object Model (POM), custom fixtures, typed test data, environment-driven configuration, reports, traces, screenshots, videos, and reusable utilities for the UI booking journey.

## Structure

| Folder | Purpose |
| --- | --- |
| `pages/` | Reusable UI actions and locators. No assertions belong here. |
| `tests/` | Test scenarios and all assertions. |
| `fixtures/` | Custom Playwright fixtures for pages and test data. |
| `test-data/` | JSON-driven booking data for the scenario. |
| `types/` | Shared TypeScript data contracts. |
| `utils/` | Configuration, date, logging, and masking helpers. |

## Setup

```bash
npm install
Copy-Item .env.example .env
npx playwright install
```

## E07 assignment coverage

The UI suite is configured for Chandana / E07: one-way business MAA → HYD, with travel date derived from `daysToTravel` in the JSON fixture and PNR ownership asserted as `TS-<employeeId>-<sequence>`. It uses role-first and accessible-name locators so the specified `span` → `div` DOM change does not affect the flow. The UI-only implementation keeps assertions and journey orchestration in the tests, while page objects remain focused on locators and actions. The performance gate checks seat-map rendering with `SEAT_MAP_MAX_RENDER_MS`.

API/DB security checks (including the required BOLA 403) intentionally belong to the separate Java project and are not represented as a UI assertion.

See [test-data classification](docs/TEST-DATA-CLASSIFICATION.md) for the public-versus-secret data boundary and CI secret-scan gate.

## Run tests

```bash
npm test
npm run test:ui
npm run test:performance
npm run typecheck
npm run test:headed
npm run test:debug
npm run report
```

Run a single browser with `npx playwright test --project=chromium`.

## Environment settings

Configure `.env` when needed:

```text
BASE_URL=https://tripstack.doomple.com/
HEADLESS=true
SLOW_MO=0
TRIPSTACK_EMAIL=your@email.com
TRIPSTACK_PASSWORD=your-password
PASSENGER_FIRST_NAME_PREFIX=Test
PASSENGER_LAST_NAME_PREFIX=User
SEAT_MAP_MAX_RENDER_MS=5000
```

Copy `.env.example` to `.env` and populate the required values locally. Only login credentials and environment settings remain in `.env`; booking data lives in `test-data/booking-data.json`, and passenger/payment details are generated dynamically per run.

Reports are saved in `playwright-report/`. Failure screenshots/videos and first-retry traces are uploaded as CI artifacts. Framework log messages automatically redact known runtime secret values.
