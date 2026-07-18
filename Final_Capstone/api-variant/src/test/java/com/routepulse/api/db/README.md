# Database Testing Setup

## Overview
The framework includes database integration testing to verify data integrity and ownership assertions for booking operations. The database tests are **optional** and gracefully skip if the database is unavailable.

## Architecture

### Components
1. **DatabaseConnection.java** - Manages JDBC connections
2. **BookingQueries.java** - SQL queries to fetch booking data
3. **BookingDataAssertions.java** - Assertions for data integrity and ownership
4. **BookingLifecycleIntegrationTest.java** - Integration test combining API and DB validation

### Features
- ✅ Verify booking state transitions (HELD → PAYMENT_PENDING → CONFIRMED)
- ✅ Validate data integrity (amounts, seat IDs, inventory match)
- ✅ Confirm ownership (booking belongs to logged-in employee)
- ✅ PNR generation and retrieval
- ✅ Graceful degradation (tests pass without DB access)

## Setup

### Prerequisites
- PostgreSQL database (or any SQL database with JDBC driver)
- Database must have `bookings` table with columns:
  - `id` (UUID/String)
  - `pnr` (String, nullable)
  - `state` (String: HELD, PAYMENT_PENDING, CONFIRMED, CANCELLED, REFUNDED)
  - `emp_id` (Integer)
  - `inventory_id` (String)
  - `seat_ids` (String/Array)
  - `amount_paise` (Long)
  - `refundable` (Boolean)
  - `hold_expires_at` (Timestamp)

### Environment Variables

Set these environment variables to enable database testing:

```bash
# PostgreSQL connection (required for DB tests)
export TRIPSTACK_DB_URL="jdbc:postgresql://localhost:5432/tripstack"
export TRIPSTACK_DB_USER="postgres"
export TRIPSTACK_DB_PASSWORD="your_password"
```

Or pass as Maven system properties:

```bash
mvn test \
  -DTRIPSTACK_DB_URL=jdbc:postgresql://localhost:5432/tripstack \
  -DTRIPSTACK_DB_USER=postgres \
  -DTRIPSTACK_DB_PASSWORD=password
```

### Running the Integration Test

```bash
# Run with database enabled
mvn -Dtest=BookingLifecycleIntegrationTest test

# Run all tests (database tests included if DB available)
mvn clean test
```

## Expected Output

When database is available:

```
=== Stage 3: Hold Seats ===
✓ API: Seats held - Booking ID: d39bcc11-8a77-4624-a8e8-32bc5537f6fa

=== Stage 4: Database Verification - Hold State ===
✓ Database: Booking held correctly - State: HELD, EmpId: 1018
✓ Database: Refundable flag verified - true
✓ Database: Ownership verified - Booking belongs to employee 1018

=== Stage 5: Process Payment ===
✓ API: Payment processed - Status: PAYMENT_PENDING

=== Stage 6: Database Verification - Payment State ===
✓ Database: Booking in PAYMENT_PENDING state - Amount: 182490

=== Stage 7: Confirm Booking ===
✓ API: Booking confirmed - PNR: TS-1018-0025

=== Stage 8: Database Verification - Confirmed State ===
✓ Database: Booking CONFIRMED with PNR: TS-1018-0025
✓ Database: Booking retrieved by PNR - State: CONFIRMED
```

When database is **NOT** available:

```
⚠️  Database connection failed: Connection refused
⚠️  Database tests will be skipped
✓ API: Seats held - Booking ID: d39bcc11-8a77-4624-a8e8-32bc5537f6fa
[Continues with API-only tests]
```

## Database Schema Example

```sql
CREATE TABLE bookings (
    id UUID PRIMARY KEY,
    pnr VARCHAR(20),
    state VARCHAR(20) CHECK (state IN ('HELD', 'PAYMENT_PENDING', 'CONFIRMED', 'CANCELLED', 'REFUNDED')),
    emp_id INTEGER NOT NULL,
    inventory_id VARCHAR(50) NOT NULL,
    seat_ids TEXT NOT NULL,
    amount_paise BIGINT NOT NULL,
    refundable BOOLEAN DEFAULT true,
    hold_expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_bookings_pnr ON bookings(pnr);
CREATE INDEX idx_bookings_emp_id ON bookings(emp_id);
```

## Assertions Available

### Booking State Verification
- `assertBookingHeldInDatabase()` - Verify HELD state, employee ownership
- `assertBookingPaymentPendingInDatabase()` - Verify PAYMENT_PENDING state, amount
- `assertBookingConfirmedInDatabase()` - Verify CONFIRMED state, PNR generation

### Data Integrity
- `assertSeatIdsMatch()` - Verify seat IDs match API response
- `assertRefundableFlag()` - Verify refundability preservation
- `assertBookingRetrievableByPnr()` - Verify PNR-based lookup works

### Ownership
- `assertBookingOwnership()` - Verify booking belongs to expected employee
- `assertEmployeeHasBooking()` - Verify employee record contains booking

## Troubleshooting

### Database Not Connected
```
⚠️  Database connection failed: Connection refused
Ensure TRIPSTACK_DB_URL, TRIPSTACK_DB_USER, TRIPSTACK_DB_PASSWORD env vars are set
```

**Solution:** Set environment variables and restart Maven.

### PostgreSQL Driver Not Found
```
PostgreSQL Driver not found. Database tests will skip.
Add 'org.postgresql:postgresql' dependency to pom.xml
```

**Solution:** Already added to pom.xml. Run `mvn clean install` to download.

### Table Not Found
```
Error fetching booking: ERROR: relation "bookings" does not exist
```

**Solution:** Ensure database schema is initialized with bookings table.

## Integration with CI/CD

For CI/CD pipelines with database available:

```yaml
# GitHub Actions example
- name: Run tests with database
  env:
    TRIPSTACK_DB_URL: jdbc:postgresql://postgres:5432/tripstack
    TRIPSTACK_DB_USER: postgres
    TRIPSTACK_DB_PASSWORD: password
  run: mvn clean test
```

For CI/CD without database:

```yaml
- name: Run API tests (no database)
  run: mvn clean test
```

Tests will automatically detect database availability and adjust assertions.

## Limitations

- Database assertions skip gracefully if DB unavailable
- Currently supports PostgreSQL; adding other databases requires new dialect implementation
- PNR format assumed: `TS-<empId>-<sequence>`

## Future Enhancements

- [ ] Support MySQL/MariaDB via configurable JDBC drivers
- [ ] Query builder for complex booking scenarios
- [ ] Performance benchmarking with database
- [ ] Data consistency checks across replicas
- [ ] Audit trail verification
