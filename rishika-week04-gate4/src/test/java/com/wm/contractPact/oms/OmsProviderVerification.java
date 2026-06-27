package com.wm.contractPact.oms;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Provider("oms-provider")
@PactFolder("target/pacts")
public class OmsProviderVerification {

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget(
                "127.0.0.1",
                wireMock.getPort()
        ));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Order 501 exists")
    void order501Exists() {

        wireMock.stubFor(get(urlEqualTo("/order/501"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                          "orderId":501,
                          "customerName":"Rishika",
                          "status":"SHIPPED",
                          "amount":1599.50
                        }
                        """)));
    }

    @State("Order 700 exists")
    void order700Exists() {

        wireMock.stubFor(get(urlEqualTo("/order/700"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                          "orderId":700,
                          "customerName":"John",
                          "status":"PROCESSING",
                          "amount":850.00
                        }
                        """)));
    }

    @State("Provider accepts new orders")
    void providerAcceptsOrders() {

        wireMock.stubFor(post(urlEqualTo("/order"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                          "orderId":501,
                          "status":"CREATED",
                          "amount":1599.50
                        }
                        """)));
    }
}