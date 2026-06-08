# SDET Playwright Assessment
 
This repository contains Playwright end-to-end tests for a sample retail shopping application. The tests validate key customer journeys such as login, product search, cart updates, checkout, and order viewing.
  
## Prerequisites
 
- Node.js installed
- Playwright installed in the test project
- Retail application running locally or available through a configured URL
 
## Setup
 
From the `shop-tests` directory, install dependencies:
 
```bash
npm install
```
 
Install Playwright browsers if they are not already installed:
 
```bash
npx playwright install
```
 
## Configuration
 
The Playwright config is located at:
 
```text
shop-tests/playwright.config.ts
```
 
By default, tests run against:
 
```text
http://localhost:5173
```
 
To run against a different application URL, set `BASE_URL`:
 
```bash
BASE_URL=https://your-app-url.com npx playwright test
```
 
On Windows PowerShell:
 
```powershell
$env:BASE_URL="https://your-app-url.com"
npx playwright test
```
 
Optional test user values can be configured with:
 
```text
TEST_EMAIL
TEST_PASSWORD
```
 
## Running Tests
 
Run all tests:
 
```bash
npx playwright test
```
 
Run a specific test file:
 
```bash
npx playwright test tests/login-flow.spec.ts
```
 
Run tests in headed mode:
 
```bash
npx playwright test --headed
```
 
Debug tests:
 
```bash
npx playwright test --debug
```
 
## Test Coverage
 
Current test files include:
 
- `login-flow.spec.ts` - verifies the login page and sign-in flow.
- `search-to-cart.spec.ts` - searches for Running Shoes and adds the product to the cart.
- `cart-checkout.spec.ts` - validates the cart-to-checkout journey.
- `chekcout-order.spec.ts` - places an order and verifies the orders page.
- `a11y.spec.ts` - reserved for accessibility tests.
- `edge-case.spec.ts` - reserved for edge case tests.
 
## Page Objects
 
The test suite uses the Page Object Model:
 
- `LoginPage.ts` contains login page checks and sign-in actions.
- `SearchPage.ts` handles catalog search and add-to-cart actions.
- `CartPage.ts` handles cart navigation and checkout entry.
- `CheckoutPage.ts` handles placing an order and viewing orders.
 
## Reports and Artifacts
 
The current reporter is configured as `blob`. On failures, Playwright retains:
 
- Trace files
- Screenshots
- Videos
 
These artifacts help investigate test failures after a run.
 
## Notes
 
- The project currently runs on Chromium only.
- The default local app URL is `http://localhost:5173`.
- Some test files are placeholders and can be expanded with accessibility and edge case scenarios.
 