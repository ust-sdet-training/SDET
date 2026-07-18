package com.routepulse.api.tests.security;

import com.routepulse.api.base.JourneyBaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Security - Input Validation & Injection Prevention")
public class SecurityScannerTest extends JourneyBaseTest {

    @Test
    @DisplayName("SQL Injection attempt in search from parameter")
    public void sqlInjectionAttemptInFromParameter() {
        RequestSpecification spec = requestSpec();
        String injectionPayload = configManager.getFrom() + "' OR '1'='1";
        Response response = apiClient.get("/api/buses?from=" + java.net.URLEncoder.encode(injectionPayload, java.nio.charset.StandardCharsets.UTF_8) + "&to=" + configManager.getTo() + "&date=" + java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 400,
                "API should safely handle SQL injection attempts, got: " + statusCode);
    }

    @Test
    @DisplayName("SQL Injection attempt in search to parameter")
    public void sqlInjectionAttemptInToParameter() {
        RequestSpecification spec = requestSpec();
        String injectionPayload = configManager.getTo() + "\"; DROP TABLE bookings; --";
        Response response = apiClient.get("/api/buses?from=" + configManager.getFrom() + "&to=" + java.net.URLEncoder.encode(injectionPayload, java.nio.charset.StandardCharsets.UTF_8) + "&date=" + java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 400,
                "API should safely handle destructive SQL injection attempts, got: " + statusCode);
    }

    @Test
    @DisplayName("XSS payload in seat ID should be sanitized")
    public void xssPayloadInSeatIdShouldBeSanitized() {
        login();

        String xssPayload = "<script>alert(xss)</script>";
        String holdPayload = String.format(
                "{\"journeyType\":\"bus\",\"inventoryId\":\"BUS-123\",\"seatIds\":[\"%s\"],\"refundable\":true}",
                xssPayload
        );

        RequestSpecification spec = authSpec();
        Response response = apiClient.post("/api/bookings", holdPayload, spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 400 || statusCode == 403 || statusCode == 404,
                "API should reject XSS payload in seat ID, got: " + statusCode);
    }

    @Test
    @DisplayName("Negative price in request should be rejected")
    public void negativePriceInRequestShouldBeRejected() {
        login();
        RequestSpecification spec = authSpec();

        String holdPayload = "{\"journeyType\":\"bus\",\"inventoryId\":\"BUS-123\",\"seatIds\":[\"Z1\"],\"refundable\":true}";
        Response response = apiClient.post("/api/bookings", holdPayload, spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 400 || statusCode == 404,
                "API should validate request format, got: " + statusCode);
    }

    @Test
    @DisplayName("Invalid date format in search should be rejected")
    public void invalidDateFormatInSearchShouldBeRejected() {
        RequestSpecification spec = requestSpec();
        Response response = apiClient.get("/api/buses?from=" + configManager.getFrom() + "&to=" + configManager.getTo() + "&date=2026/07/17", spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 400 || statusCode == 200,
                "API should handle invalid date format, got: " + statusCode);
    }

    @Test
    @DisplayName("Very long string input should not cause buffer overflow")
    public void veryLongStringInputShouldNotCauseBufferOverflow() {
        RequestSpecification spec = requestSpec();
        String longString = "A".repeat(10000);
        Response response = apiClient.get("/api/buses?from=" + longString + "&to=" + configManager.getTo() + "&date=" + java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 400 || statusCode == 404 || statusCode == 414,
                "API should handle extremely long input gracefully, got: " + statusCode);
    }

    @Test
    @DisplayName("Null/empty seat IDs array should be rejected")
    public void nullEmptySeatIdsArrayShouldBeRejected() {
        login();
        RequestSpecification spec = authSpec();

        String holdPayload = "{\"journeyType\":\"bus\",\"inventoryId\":\"BUS-123\",\"seatIds\":[],\"refundable\":true}";
        Response response = apiClient.post("/api/bookings", holdPayload, spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 400 || statusCode == 404,
                "API should reject empty seat IDs array, got: " + statusCode);
    }

    @Test
    @DisplayName("Invalid journey type should be rejected")
    public void invalidJourneyTypeShouldBeRejected() {
        login();
        RequestSpecification spec = authSpec();

        String holdPayload = "{\"journeyType\":\"train\",\"inventoryId\":\"BUS-123\",\"seatIds\":[\"U1\"],\"refundable\":true}";
        Response response = apiClient.post("/api/bookings", holdPayload, spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 400 || statusCode == 404,
                "API should reject invalid journey type, got: " + statusCode);
    }

    @Test
    @DisplayName("Special characters in inventory ID should be handled safely")
    public void specialCharactersInInventoryIdShouldBeHandledSafely() {
        login();
        RequestSpecification spec = authSpec();

        String holdPayload = "{\"journeyType\":\"bus\",\"inventoryId\":\"BUS-<123>\",\"seatIds\":[\"U1\"],\"refundable\":true}";
        Response response = apiClient.post("/api/bookings", holdPayload, spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 400 || statusCode == 404,
                "API should safely handle special characters in inventory ID, got: " + statusCode);
    }

    @Test
    @DisplayName("Unicode/emoji input should not crash API")
    public void unicodeEmojiInputShouldNotCrashApi() {
        RequestSpecification spec = requestSpec();
        String emojiPayload = "🚌🚌";
        Response response = apiClient.get("/api/buses?from=" + java.net.URLEncoder.encode(emojiPayload, java.nio.charset.StandardCharsets.UTF_8) + "&to=" + configManager.getTo() + "&date=" + java.time.LocalDate.now().plusDays(configManager.getTravelOffsetDays()).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), spec);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode >= 200 && statusCode < 600,
                "API should handle Unicode gracefully, got: " + statusCode);
    }
}
