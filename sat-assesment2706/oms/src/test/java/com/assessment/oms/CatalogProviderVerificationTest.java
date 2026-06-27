package com.assessment.oms;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Provider("oms")
@PactBroker(
    url = "https://qwq.pactflow.io",
    authentication = @PactBrokerAuth(
        token = "DrLusdyyj6UlhPprN3cneQ"
    )
)
public class CatalogProviderVerificationTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startServer() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
    }

    @AfterAll
    static void stopServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        wireMockServer.resetAll();
        configureFor("localhost", 8090);
        context.setTarget(new HttpTestTarget("localhost", 8090));
    }

    @State("SKU exists")
    public void skuExists() {
        stubFor(get(urlEqualTo("/catalog/RUN-101"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "sku":"RUN-101",
                                  "name":"Running Shoes",
                                  "price":120.5,
                                  "availability":true
                                }
                                """)));
    }

    @State("SKU missing")
    public void skuMissing() {
        stubFor(get(urlEqualTo("/catalog/UNKNOWN"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "message":"Product not found"
                                }
                                """)));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }
}