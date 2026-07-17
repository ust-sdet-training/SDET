package com.tripstack.tests.security;

import com.tripstack.base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("api")
@Tag("security")
public class PrivilegeEscalationTest extends BaseTest {

    @Test
    void travellerAccessingAdminRouteIsForbidden() {
        authClient.adminPing(token).then()
                .statusCode(403);
    }
}