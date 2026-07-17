# API Test Suite - E05 Bhumika (Flight Round-Trip: PUN → BOM)

## Project Information
- **Employee ID**: 1005
- **Email**: erin@tripstack.test
- **Password**: Password@123
- **Route**: PUN (Pune) → BOM (Mumbai) - Round-trip flight
- **Cabin Class**: Economy
- **Travel Date Offset**: +7 days from today
- **Day-6 Fault**: Seat-hold expiry
- **Performance Target**: Checkout endpoint
- **Security Negative**: Expired token
- **v2 DOM Change**: Node reorder

## Test Execution Status
✅ **All Tests Passing (4/4)**
- AuthTest: ✓ (login + reset namespace)
- BookingLifecycleTest: ✓ (complete flight booking workflow)
- FlightSearchTest: ✓ (search and seat map retrieval)
- SecurityNegativeTest: ✓ (invalid token handling)

**Total Test Time**: ~13.9 seconds
**Success Rate**: 100%

## Framework Structure
- **RestAssured + JUnit 5**: API contract testing
- **Jackson**: JSON serialization/deserialization
- **PostgreSQL JDBC**: Database assertions
- **Maven**: Build and test execution

## Key Changes from Source (E18 Lakhan - Bus Project)
1. ✅ Changed email from `rupert@tripstack.test` to `erin@tripstack.test`
2. ✅ Changed Employee ID from 1018 to 1005
3. ✅ Changed date offset from +17 days to +7 days
4. ✅ Changed transport from Bus to Flight
5. ✅ Changed route from BLR→HYD (Bangalore to Hyderabad) to PUN→BOM (Pune to Mumbai)
6. ✅ Updated all service classes to use FlightService instead of BusService
7. ✅ Added DB credentials to ConfigManager
8. ✅ Added PostgreSQL JDBC driver to pom.xml

## Test Scenarios Covered

### 1. Authentication & Namespace Reset (AuthTest)
- Login with erin@tripstack.test
- Verify token retrieval
- Reset namespace for clean state

### 2. Complete Booking Lifecycle (BookingLifecycleTest)
- Search for flights PUN→BOM on +7 days
- Retrieve seat map
- Hold 2 seats
- Process payment
- Confirm booking with PNR generation
- **PNR Format**: TS-<empId>-<seq> (e.g., TS-1005-0001)

### 3. Flight Search & Seat Availability (FlightSearchTest)
- Search flights for PUN→BOM route
- Retrieve seat map for selected flight
- Validate seat availability

### 4. Security - Invalid Token Handling (SecurityNegativeTest)
- Verify public endpoints work with invalid/missing tokens
- Flight search should return 200 even with invalid Authorization header

## Database Testing
- PostgreSQL connection configured
- Ready for namespace/emp_id assertions
- Booking records namespaced by employee ID (1005)

## Project Ready For
- ✅ Full CI/CD pipeline integration
- ✅ Performance baseline establishment
- ✅ Security gate implementation
- ✅ Day-6 resilience injection testing
- ✅ Live demo and viva
