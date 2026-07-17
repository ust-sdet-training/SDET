package com.tripstack.tests.auth;

import com.tripstack.base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Tag("api")
@Tag("auth")
public class AuthMeTest extends BaseTest {

    @Test
    void meReturnsAuthenticatedUserDetails() {
        authClient.me(token).then()
                .statusCode(200)
                .body("email", equalToIgnoringCase("niaj@tripstack.test"))
                .body("displayName", notNullValue())
                .body("role", notNullValue());
    }
}