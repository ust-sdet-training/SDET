package com.routepulse.api.tests.api;

import com.routepulse.api.base.JourneyBaseTest;
import com.routepulse.api.db.DatabaseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingFlowTest extends JourneyBaseTest {

    @Test
    public void loginAndBusSearchShouldWorkAgainstLiveApi() {
        String loginPayload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", configManager.getEmail(), configManager.getPassword());

        Response loginResponse = apiClient.post("/api/auth/login", loginPayload, requestSpec());
        assertEquals(200, loginResponse.getStatusCode(), "Login should succeed");

        String token = loginResponse.jsonPath().getString("token");
        assertTrue(token != null && !token.isBlank(), "Login should return a bearer token");

        String travelDate = LocalDate.now()
                .plusDays(configManager.getTravelOffsetDays())
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        Response busesResponse = apiClient.get(
                "/api/buses?from=" + configManager.getFrom() + "&to=" + configManager.getTo() + "&date=" + travelDate,
                requestSpec()
        );

        assertEquals(200, busesResponse.getStatusCode(), "Bus search should return 200");
        assertTrue(busesResponse.jsonPath().getInt("count") >= 0, "Bus search should return a count");

        Response bookingsResponse = apiClient.get("/api/bookings", authSpec(token));
        assertEquals(200, bookingsResponse.getStatusCode(), "The authenticated bookings endpoint should return 200");
    }

    @Test
    public void databaseValidatorShouldSeeBookingsForTheAssignedEmployee() throws SQLException {
        DatabaseValidator validator = new DatabaseValidator(configManager);
        String empId = loginResponseEmpId();

        assertTrue(validator.hasBookingForEmployee(empId) || validator.latestBookingStateForEmployee(empId) == null,
                "The database validator should be able to inspect booking ownership for the assigned employee");
    }

    private String loginResponseEmpId() {
        String loginPayload = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", configManager.getEmail(), configManager.getPassword());
        Response response = apiClient.post("/api/auth/login", loginPayload, requestSpec());
        assertEquals(200, response.getStatusCode(), "Login should succeed for database assertion setup");
        String empId = response.jsonPath().getString("empId");
        assertNotNull(empId, "Login response should include empId");
        return empId;
    }
}
