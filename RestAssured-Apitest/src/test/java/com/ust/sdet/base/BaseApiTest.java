package com.ust.sdet.base;

import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseApiTest
        extends BaseTest {

    protected void assertStatus(
            Response response,
            int expectedStatus
    ) {
        assertNotNull(
                response
        );

        assertEquals(
                expectedStatus,
                response.statusCode()
        );
    }

    protected void assertResponseBody(
            Response response
    ) {
        assertNotNull(
                response
        );
        assertFalse(
                response.asString()
                        .isBlank()

        );
    }

    protected void assertHeaderPresent(
            Response response,
            String header

    ) {
        assertNotNull(
                response.getHeader(
                        header
                )
        );
    }

    protected void assertLocationHeader(
            Response response
    ) {
        assertHeaderPresent(
                response,
                "Location"
        );
    }
}