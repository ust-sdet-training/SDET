package com.routepulse.api.tests.api;

import com.routepulse.api.base.JourneyBaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTest extends JourneyBaseTest {

    @Test
    public void loginAndResetShouldWork() {
        login();
        assertNotNull(token, "Token should be returned after login");

        resetNamespace();
    }
}
