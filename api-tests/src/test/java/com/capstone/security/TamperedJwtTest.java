package com.capstone.security;

import com.capstone.support.BaseApiTest;
import com.capstone.support.BaseData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("security")
public class TamperedJwtTest extends BaseApiTest {

    @Test
    void rejectsTamperedToken() {
        Response response = bookingClient.getMyBookings(BaseData.TAMPERED_TOKEN);

        assertEquals(401, response.statusCode());
    }
}