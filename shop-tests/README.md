# SDET Playwright TypeScript

Playwright starter framework for the UST Global SDET training retail app.

The application architecture is planned as:

```text
ReactJS frontend
  |
POS / retail API service
  |
  |-- OMS service
  |-- WireMock external services
```

Playwright is mainly used for Week 1 UI automation and later Week 7 integrated E2E flows. API calls in Playwright should target the POS/retail API unless a lab explicitly asks for OMS or contract-level validation.

## Setup

```bash
npm install
npx playwright install
npm test
```

Set the app URL when needed:

```bash
BASE_URL=http://localhost:5173 npm test
POS_API_URL=http://localhost:4000 npm run test:api
```

Day 1 app-launch validation:

```bash
npm run test:day1
```

Day 2 login workflow:

```bash
npm run test:day2
```

Optional credential override:

```bash
TEST_EMAIL=customer@example.com TEST_PASSWORD=Password@123 npm run test:day2
```

## Structure

- `tests/`: Test specifications.
- `pages/`: Page objects.
- `fixtures/`: Test fixtures and authenticated state.
- `utils/`: Reusable helpers and test data.

## Training Alignment

- Week 1: ReactJS UI automation against login, products, cart, and checkout.
- Week 5: Debugging seeded UI/API defects with traces and artifacts.
- Week 7: Integrated UI + API + DB + contract evidence in the final capstone.
