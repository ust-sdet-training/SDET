package com.ust.sdet.api.tests.api;

import com.ust.sdet.api.base.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTest extends BaseTest {

    @Test
    public void loginAndResetShouldWork() {
        login();
        assertNotNull(token, "Token should be returned after login");

        resetNamespace();
    }
}
