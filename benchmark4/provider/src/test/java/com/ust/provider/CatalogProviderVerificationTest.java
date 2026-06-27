package com.ust.provider;

import au.com.dius.pact.provider.junit5.*;
import au.com.dius.pact.provider.junitsupport.*;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Provider("catalog-service")
@PactFolder("../consumer/target/pacts")
public class CatalogProviderVerificationTest {

    static WireMockServer wireMock = new WireMockServer(8089);

    @BeforeAll
    static void start() {
        wireMock.start();
    }
    @AfterAll
    static void stop() {
        wireMock.stop();
    }
    @BeforeEach
    void setup(PactVerificationContext context){
        context.setTarget(new HttpTestTarget("localhost", 8089));
    }
    @TestTemplate
    @ExtendWith(
            PactVerificationInvocationContextProvider.class
    )
    void verify(PactVerificationContext context){
        context.verifyInteraction();
    }
    @State("SKU-1 exists")
    void skuExists() {
        wireMock.stubFor(
                get(urlEqualTo("/catalog/SKU-1"))
                        .willReturn(
                                okJson("""
                                {
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
                        .willReturn(notFound())
        );
    }
}
