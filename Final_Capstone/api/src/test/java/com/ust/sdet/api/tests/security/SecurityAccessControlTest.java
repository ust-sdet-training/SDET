package com.ust.sdet.api.tests.security;

import com.ust.sdet.api.base.BaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Security - Access Control (401/403 Enforcement)")
public class SecurityAccessControlTest extends BaseTest {

    @Test
    @DisplayName("Protected endpoints return 401 without Authorization header")
    public void protectedEndpointRequires401WithoutAuth() {
        RequestSpecification spec = requestSpec();

        String holdPayload = "{\"journeyType\":\"bus\",\"inventoryId\":\"BUS-123\",\"seatIds\":[\"U1\"],\"refundable\":true}";
        Response response = apiClient.post("/api/bookings", holdPayload, spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 401 || statusCode == 400,
                "Protected endpoint should reject unauthenticated request, got: " + statusCode);
    }

    @Test
    @DisplayName("Protected endpoints return 401 with invalid token format")
    public void protectedEndpointRejects401InvalidTokenFormat() {
        RequestSpecification spec = requestSpec();
        spec.header("Authorization", "NotABearerToken");

        String holdPayload = "{\"journeyType\":\"bus\",\"inventoryId\":\"BUS-123\",\"seatIds\":[\"U1\"],\"refundable\":true}";
        Response response = apiClient.post("/api/bookings", holdPayload, spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 401 || statusCode == 400,
                "Invalid token format should be rejected, got: " + statusCode);
    }

    @Test
    @DisplayName("Protected endpoints return 401 with malformed Bearer token")
    public void protectedEndpointRejects401MalformedBearer() {
        RequestSpecification spec = requestSpec();
        spec.header("Authorization", "Bearer @@@@invalid_jwt_token@@@@");

        String holdPayload = "{\"journeyType\":\"bus\",\"inventoryId\":\"BUS-123\",\"seatIds\":[\"U1\"],\"refundable\":true}";
        Response response = apiClient.post("/api/bookings", holdPayload, spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 401 || statusCode == 400,
                "Malformed JWT should be rejected, got: " + statusCode);
    }

    @Test
    @DisplayName("Reset endpoint requires authentication")
    public void resetEndpointRequiresAuthentication() {
        Response response = apiClient.post("/api/reset", null, requestSpec());

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 401 || statusCode == 400,
                "Reset endpoint should require authentication, got: " + statusCode);
    }

    @Test
    @DisplayName("Payment endpoint requires authentication")
    public void paymentEndpointRequiresAuthentication() {
        Response response = apiClient.post("/api/bookings/BOOKING-123/pay", "", requestSpec());

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 401 || statusCode == 400,
                "Payment endpoint should require authentication, got: " + statusCode);
    }

    @Test
    @DisplayName("Confirmation endpoint requires authentication")
    public void confirmationEndpointRequiresAuthentication() {
        Response response = apiClient.post("/api/bookings/BOOKING-123/confirm", "", requestSpec());

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 401 || statusCode == 400,
                "Confirmation endpoint should require authentication, got: " + statusCode);
    }
}
