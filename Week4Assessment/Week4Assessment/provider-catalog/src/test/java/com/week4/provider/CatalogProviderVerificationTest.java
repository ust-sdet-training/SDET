package com.week4.provider;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.HttpURLConnection;
import java.net.URL;

public class CatalogProviderVerificationTest {

    static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", 8089);
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }

    @Test
    void verifySkuExists() throws Exception {

        stubFor(get(urlEqualTo("/catalog/SKU-1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                              "sku":"SKU-1",
                              "availability":"IN_STOCK",
                              "priceMinor":129900
                            }
                        """)));

        URL url = new URL("http://localhost:8089/catalog/SKU-1");
        HttpURLConnection con =
                (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        int response = con.getResponseCode();

        assertEquals(200, response);
    }
}