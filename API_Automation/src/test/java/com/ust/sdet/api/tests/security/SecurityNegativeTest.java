package com.ust.sdet.api.tests.security;

import com.ust.sdet.api.base.BaseTest;
import com.ust.sdet.api.client.ApiClient;
import com.ust.sdet.api.config.ConfigManager;
import com.ust.sdet.api.specs.RequestSpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecurityNegativeTest extends BaseTest {

    @Test
    public void invalidTokenShouldStillAllowPublicFlightSearch() {
        RequestSpecFactory specFactory = new RequestSpecFactory();
        ConfigManager config = ConfigManager.getInstance();

        RequestSpecification spec = specFactory.build(config);
        spec.header("Authorization", "Bearer invalid_token");

        ApiClient client = new ApiClient();
        Response response = client.get("/api/flights?from=PUN&to=BOM&date=2026-07-17&pax=1&class=economy", spec);

        assertEquals(200, response.getStatusCode(), "Public flight search should return 200 regardless of token");
    }
}
