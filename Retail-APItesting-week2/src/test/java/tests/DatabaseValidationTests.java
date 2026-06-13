package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseValidationTests
        extends BaseTest {

    @Test
    @DisplayName("Verify database connectivity")
    void verifyDatabaseConnectivity()
            throws Exception {

        assertTrue(
                dbSupport.isReachable()
        );
    }
}

