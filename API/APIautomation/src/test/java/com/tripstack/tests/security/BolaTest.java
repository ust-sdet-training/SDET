package com.tripstack.tests.security;

import com.tripstack.base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("api")
@Tag("security")
public class BolaTest extends BaseTest {

    // A PNR belonging to a DIFFERENT employee - provided by the director on Day 6.
    // Never hardcode your own PNR here.
    private static final String OTHER_EMPLOYEE_PNR = System.getenv("TRIPSTACK_OTHER_EMP_PNR");

    @Test
    void readingAnotherEmployeesPnrIsForbidden() {
        assumeTrue(OTHER_EMPLOYEE_PNR != null && !OTHER_EMPLOYEE_PNR.isBlank(),
                "Skipping - TRIPSTACK_OTHER_EMP_PNR not set (needs director-provided PNR)");

        bookingClient.getByPnr(token, OTHER_EMPLOYEE_PNR).then()
                .statusCode(403);
    }
}