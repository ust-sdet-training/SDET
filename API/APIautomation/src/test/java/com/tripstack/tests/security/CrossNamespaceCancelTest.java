package com.tripstack.tests.security;

import com.tripstack.base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("api")
@Tag("security")
public class CrossNamespaceCancelTest extends BaseTest {

    // A bookingId belonging to a DIFFERENT employee - provided by the director on Day 6.
    private static final String OTHER_EMPLOYEE_BOOKING_ID = System.getenv("TRIPSTACK_OTHER_EMP_BOOKING_ID");

    @Test
    void cancellingAnotherEmployeesBookingIsForbidden() {
        assumeTrue(OTHER_EMPLOYEE_BOOKING_ID != null && !OTHER_EMPLOYEE_BOOKING_ID.isBlank(),
                "Skipping - TRIPSTACK_OTHER_EMP_BOOKING_ID not set (needs director-provided bookingId)");

        bookingClient.cancel(token, OTHER_EMPLOYEE_BOOKING_ID).then()
                .statusCode(403);
    }
}