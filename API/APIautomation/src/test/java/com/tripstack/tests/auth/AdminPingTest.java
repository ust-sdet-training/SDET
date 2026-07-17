package com.tripstack.tests.auth;

import com.tripstack.base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("api")
@Tag("security")
public class AdminPingTest extends BaseTest {

    @Test
    void travellerCannotAccessAdminRoute() {
        authClient.adminPing(token).then()
                .statusCode(403);
    }
}