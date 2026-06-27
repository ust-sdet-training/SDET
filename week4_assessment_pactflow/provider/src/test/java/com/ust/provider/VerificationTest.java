package com.ust.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Provider("catalog-service")
@PactFolder("../consumer/target/pacts")
public class VerificationTest {

    private static final WireMockServer wireMock = new WireMockServer(8089);

    @BeforeAll
    static void startServer() {
        wireMock.start();
    }

    @AfterAll
    static void stopServer() {
        wireMock.stop();
    }

    @BeforeEach
    void beforeEach(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8089));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("SKU-1 exists")
    void skuExists() {

        wireMock.stubFor(
                get(urlEqualTo("/catalog/SKU-1"))
                        .willReturn(
                                okJson("""
                                {
                                  "sku":"SKU-1",
                                  "name":"Laptop",
                                  "priceMinor":129900,
                                  "availability":"IN_STOCK"
                                }
                                """)
                        )
        );
    }

    @State("SKU-404 missing")
    void skuMissing() {

        wireMock.stubFor(
                get(urlEqualTo("/catalog/SKU-404"))
                        .willReturn(
                                notFound()
                        )
        );
    }
}