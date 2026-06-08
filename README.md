# SDET

Repository for SDET training batch

## Overview

This repository contains Playwright end-to-end tests and page objects used for SDET exercises and automation practice.

## Prerequisites

- Node.js 16+ installed
- npm or yarn

## Install

1. Install dependencies:

```bash
npm install
# or
yarn
```

## Run tests

- Run all Playwright tests:

```bash
npm test
# or with Playwright directly
npx playwright test
```

- Run a single spec:

```bash
npx playwright test shop-tests/tests/search-to-cart.spec.ts
```

## Project structure

- shop-tests/: Playwright project
  - pages/: Page objects (LoginPage, ProductPage, CartPage, SearchPage)
  - tests/: Test specs
  - flows/: Test flows and helpers
  - fixtures/: Test fixtures and configs

## Notes

- Update `playwright.config.ts` under `shop-tests/shop-tests` for browser and reporter settings.
- If you need CI integration, add Playwright commands to your pipeline.

## Contact

For questions, open an issue.
