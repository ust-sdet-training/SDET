package com.week4.provider;

import au.com.dius.pact.provider.junit5.*;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Provider("catalog-service")
@PactFolder("../consumer-storefront/target/pacts")
public class CatalogProviderPactTest {

    static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8089));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTest(PactVerificationContext context) {
        context.verifyInteraction();
    }
    @State("SKU-1 exists")
    void skuExists() {
        wireMockServer.stubFor(get(urlEqualTo("/catalog/SKU-1"))
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
    }

    @State("SKU missing")
    void skuMissing() {
        wireMockServer.stubFor(get(urlEqualTo("/catalog/SKU-999"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                          "message":"Product not found"
                        }
                        """)));
    }
}