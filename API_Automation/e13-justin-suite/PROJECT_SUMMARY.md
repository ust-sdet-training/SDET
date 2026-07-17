# API Test Suite - E13 Justin (Flight: LKO → DEL)

## Project Information
- **Employee ID**: 1013
- **Email**: mallory@tripstack.test
- **Password**: Password@123
- **Route**: LKO (Lucknow) → DEL (Delhi) - One-way flight
- **Cabin Class**: Economy
- **Travel Date Offset**: +20 days from today
- **Purpose**: Validate the same flight-booking workflow with a different identity and route variant

## Scenario Focus
- Authenticate as Mallory Menon
- Search for one-way flights from LKO to DEL
- Retrieve the seat map and hold seats
- Process payment and confirm the booking lifecycle
- Validate public and security paths for the new persona

## Framework Structure
- **RestAssured + JUnit 5**: API contract testing
- **Jackson**: JSON serialization/deserialization
- **PostgreSQL JDBC**: Database assertions
- **Maven**: Build and test execution

## Key Changes from the base suite
1. ✅ Switched persona to Justin E13 / Mallory Menon
2. ✅ Updated employee ID to 1013
3. ✅ Shifted route to LKO → DEL
4. ✅ Changed travel date offset to +20 days
5. ✅ Reframed the workflow as a one-way flight booking variant
6. ✅ Renamed the project artifact for the new scenario
