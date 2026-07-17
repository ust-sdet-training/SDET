package com.tripstack.base;

import com.tripstack.client.AuthClient;
import com.tripstack.client.BookingClient;
import com.tripstack.client.BusClient;
import com.tripstack.client.OpsClient;
import com.tripstack.config.ConfigManager;
import com.tripstack.utils.TokenManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static AuthClient authClient;
    protected static BusClient busClient;
    protected static BookingClient bookingClient;
    protected static OpsClient opsClient;
    protected static String token;

    @BeforeAll
    static void setUpBase() {
        authClient = new AuthClient();
        busClient = new BusClient();
        bookingClient = new BookingClient();
        opsClient = new OpsClient();
        token = TokenManager.getToken();
    }

    @AfterAll
    static void tearDownBase() {
        // Keep this employee's namespace clean so reruns stay deterministic
        Response r = opsClient.resetNamespace(token);
        if (r.getStatusCode() != 200) {
            System.out.println("RESET FAILED: status=" + r.getStatusCode()
                    + " body=" + r.getBody().asString());
        }
        r.then().statusCode(200);
    }

    protected String travelDate() {
        return ConfigManager.travelDate();
    }
}