package com.tripstack.tests.buses;

import com.tripstack.base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class SearchBusTest extends BaseTest {

    @Test
    public void verifyBusSearch() {

        Response response =
                busClient.searchBuses(
                        "IXC",
                        "BLR",
                        null
                );

        response.prettyPrint();
    }
}