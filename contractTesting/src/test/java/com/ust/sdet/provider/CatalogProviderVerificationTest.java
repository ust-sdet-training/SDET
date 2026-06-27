package com.ust.sdet.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;


import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Provider("catalog-service")
@PactBroker(
        url = "https://google-1139fa5e.pactflow.io",
        authentication = @PactBrokerAuth(token = "tQHRoEj1RITfBdg8AUIJJw"
        )
)
public class CatalogProviderVerificationTest {
    @RegisterExtension
    private static final WireMockExtension wireMock = WireMockExtension.newInstance()
                    .options(wireMockConfig().port(4010)).build();

    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("127.0.0.1", 4010));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(PactVerificationContext context) {context.verifyInteraction();
    }

    @State("SKU-1 exists")
    void skuExists() {
        wireMock.stubFor(get(urlEqualTo("/catalog/SKU-1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                          "sku": "SKU-1","name": "Macbook Air","price": 90000,"availability": "In_Stock"
                        }
                        """)));
    }

    @State("SKU-404 does not exist")
    void skuNotFound() {
        wireMock.stubFor(get(urlEqualTo("/catalog/SKU-404"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                          "message": "SKU not found"}
                        """)));
    }
}