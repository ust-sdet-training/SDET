# Week 1 Gate 1 - Shop Tests

Playwright tests for the Week 1 Gate 1 retail shop workflow, including login, product search, cart, checkout, edge cases, and accessibility checks.

## Prerequisites

- Node.js installed
- The SDET Retail Automation Lab app running locally
- Default app URL: `http://localhost:5173`

## Install Dependencies

Run from this `shop-tests` folder:

```powershell
npm install
npm install -D @playwright/test @axe-core/playwright
npx playwright install chromium
```

## Run Tests

Run all tests:

```powershell
npx playwright test
```

Run a specific test file:

```powershell
npx playwright test tests/week1-assessment.spec.ts
```

Run with a different app URL:

```powershell
$env:BASE_URL="http://localhost:5173"
npx playwright test
```

## Test Structure

- `pages/` - page objects for shop pages
- `flows/` - reusable user workflows
- `fixtures/` - test fixtures and test users
- `tests/` - Playwright test specs
- `playwright.config.ts` - Playwright configuration

## Submission Notes

Do not commit generated or local-only files such as `node_modules/`, `playwright-report/`, `test-results/`, `.env`, or zip files.
