package com.tripstack.support;

import com.tripstack.api.client.TripStackApiClient;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static TripStackApiClient apiClient;

    @BeforeAll
    static void setup() {
        apiClient = new TripStackApiClient();
    }
}
