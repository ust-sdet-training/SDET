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
- **Maven**: Build and test execution



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
