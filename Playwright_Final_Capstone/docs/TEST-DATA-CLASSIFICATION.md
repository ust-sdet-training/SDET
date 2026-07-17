# Test-data classification

## Public, version-controlled data

The booking route, cabin class, date offset, flight name, seat, employee ID, and performance threshold are test behaviour, not credentials. They stay in `test-data/booking-data.json` or `.env.example` so the E07 scenario is reviewable and repeatable.

## Secret, runtime-only data

The TripStack login, passenger contact details, and payment details are secret test data. They are supplied in one of two ways:

- Local runs: the ignored `.env` file.
- CI runs: GitHub Actions repository secrets with exactly the variable names in `.env.example`.

`fixtures/testData.ts` resolves those values through `getRuntimeSecrets()`; no page object, test-data JSON file, or test title contains a secret. Missing values fail early with the variable name only, never its value.

## Reporting discipline

Framework logging redacts every configured runtime secret. Do not log request headers, form values, or `.env` contents. Before recording a demo, use a dedicated non-personal test passenger and payment card, and inspect any failure artifact before sharing it.

## Security scope

The UI project has a CI secret-scan gate. The required BOLA/access-control negative and API security assertions will be implemented in the separate API/JDBC project because UI visibility is not proof of backend authorization.
