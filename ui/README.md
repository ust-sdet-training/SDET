# TripStack UI Automation Project

## Project Overview
This repository contains a Playwright + TypeScript test suite for the TripStack UI. It automates the booking flow for employee `1018` and includes negative security checks for authentication and namespace reset behavior.

## Key Features
- End-to-end booking flow for bus search, seat selection, passenger details, payment, and confirmation.
- Negative checks for:
  - invalid login credentials
  - unauthenticated access to protected pages
  - expired bearer token rejection
  - tampered bearer token rejection
  - self-service namespace reset (`POST /api/reset`)
- Shared page object model inside `pages/`
- Custom fixtures and logger setup in `fixtures/playwrightFixtures.ts`
- API helpers in `tests/apiHelpers.ts`
- Data driven test inputs in `tests/testData.ts`

## Important Files
- `package.json` — npm scripts and dev dependencies
- `playwright.config.ts` — Playwright runner configuration
- `tests/tripstack-bus-booking.spec.ts` — happy-path booking scenario
- `tests/tripstack-negative.spec.ts` — security and negative validation tests
- `tests/apiHelpers.ts` — auth and reset helper functions
- `tests/testData.ts` — environment-driven test data
- `pages/` — page object classes for UI actions

## How to Start Working
1. Open the project in VS Code.
2. Confirm the working directory is the `ui` folder.
3. Install dependencies if not already installed:
   - use `npm install` from the `ui` directory.
4. Run the full suite:
   - `npx playwright test`
5. Run smoke tests only:
   - `npx playwright test --grep @smoke`
7. Generate a perf baseline for the perf gate:
   - `npm run perf:baseline`
8. Run the perf gate check:
   - `npm run perf:check`
9. View the HTML report after a run:
   - `npx playwright show-report`

## Best Practices for AI Agents
- Use the existing page objects and fixtures rather than adding one-off locator logic.
- Prefer API-driven checks from `tests/apiHelpers.ts` for auth and reset flows.
- Keep test data centralized in `tests/testData.ts` and avoid hardcoding credentials.
- When adding coverage, follow the documented OpenAPI contract for `POST /api/auth/login`, `GET /api/auth/me`, and `POST /api/reset`.
- Use `APP_BASE_URL` and `APP_PATHS` from `constants.ts` if navigation paths are needed.
- Keep logs and test assertions focused on page states and API response codes.

## Difficulties Encountered
- PowerShell execution policy blocked direct `npm`/`npx` commands in the terminal.
- The local `allure-playwright` reporter dependency was unavailable, so the project uses standard Playwright reporters instead.
- Running browser and API tests together required verifying `request` fixture compatibility and ensuring the API helpers used the same base URL.

## Best Resolution Approach
- Use the local Playwright CLI (`node_modules/.bin/playwright.cmd`) when `npx`/`npm` is blocked.
- Build negative tests from the OpenAPI contract, especially using `ttlSeconds` for a fast expired-token scenario.
- Centralize reusable helpers in `tests/apiHelpers.ts` and shared data in `tests/testData.ts`.
- Validate changes with a full `npx playwright test` run and check the generated report.

## Notes
- The project currently passes `6` tests in the suite, including the new API-driven security cases.
- The self-serve reset endpoint is confirmed working for employee `1018`.
- A perf baseline generator is now available via `npm run perf:baseline` and can be verified with `npm run perf:check`.
