package com.tripstack.tests.buses;

import com.tripstack.base.BaseTest;
import com.tripstack.config.ConfigManager;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Tag("api")
@Tag("bus")
public class SearchBusTest extends BaseTest {

    @Test
    void searchIxcToBlrReturnsBuses() {
        busClient.searchBus(token, ConfigManager.FROM_CITY, ConfigManager.TO_CITY, travelDate())
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}