package api.tripstack.base;

import api.tripstack.clients.AuthClient;
import api.tripstack.clients.BusClient;
import api.tripstack.config.ConfigManager;
import api.tripstack.models.LoginRequest;
import api.tripstack.reporting.ExtentManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public abstract class BaseApiTest {
    protected static AuthClient authClient;
    protected static BusClient busClient;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = ConfigManager.baseUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        authClient = new AuthClient();
        busClient = new BusClient();
        ExtentManager.getInstance();
    }

    @BeforeEach
    void createTestCase(TestInfo testInfo) {
        ExtentManager.createTest(testInfo.getDisplayName());
        ExtentManager.info("Starting test: " + testInfo.getDisplayName());
        ExtentManager.info("Base URL: " + ConfigManager.baseUrl());
    }

    @AfterAll
    static void tearDown() {
        ExtentManager.flush();
    }

    protected String loginUser() {
        Response response = authClient.login(new LoginRequest(ConfigManager.email(), ConfigManager.password()));
        if (response.getStatusCode() != 200) {
            throw new IllegalStateException("Login failed: " + response.asString());
        }
        return response.jsonPath().getString("token");
    }
}
