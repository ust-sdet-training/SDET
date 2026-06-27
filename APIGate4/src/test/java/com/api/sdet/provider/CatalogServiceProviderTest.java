package com.api.sdet.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

@Provider("catalog-provider")
//@PactBroker(
//        url = "http://127.0.0.1:9292",
//        enablePendingPacts = "true",
//        providerTags = "main",
//        includeWipPactsSince = "2026-06-26"
//)
@PactBroker(
        url = "https://usta.pactflow.io",
        authentication = @PactBrokerAuth(token = "gywhJeyrwNnWZ-xHgMnT7g")
)
//@PactFolder("target/pacts")
public class CatalogServiceProviderTest {
    @RegisterExtension
    private static final WireMockExtension wireMock =
            WireMockExtension.newInstance()
                    .options(wireMockConfig().port(4010))
                    .build();


    @PactBrokerConsumerVersionSelectors
    public static SelectorBuilder consumerVersionSelectors() {
        return new SelectorBuilder();
    }

    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("127.0.0.1", 4010));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(PactVerificationContext context) {
        context.verifyInteraction();
    }


    @State("Item 9 exists")
    void itemExists() {

        wireMock.stubFor(get(urlEqualTo("/catalog/9"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                          "id": 9, "priceMinor":10000,"availability":"IN_STOCK"
                        }
                        """)));
    }

    @State("Item 1000 does not exist")
    void itemDoesNotExist() {
        wireMock.stubFor(get(urlEqualTo("/catalog/1000"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                          "error":"Product not found"
                        }
                        """)));
    }


}