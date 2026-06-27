package com.ust.sdet.api.contract.oms;

import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerConsumerVersionSelectors;
import au.com.dius.pact.provider.junitsupport.loader.SelectorBuilder;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
 
@Provider("oms-provider")
@PactBroker(

        url = "${pact.broker.url}",
        authentication = @PactBrokerAuth(token = "${pact.broker.token}")

)
// @PactFolder("target/pacts")
public class OmsProviderVerification {

    @RegisterExtension
    private static final WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @PactBrokerConsumerVersionSelectors
    public static SelectorBuilder consumerVersionSelectors() {
        return new SelectorBuilder();
    }

    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("127.0.0.1", wireMock.getPort()));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(PactVerificationContext context) {
        context.verifyInteraction();
    }

  @State("Sku-786 is exist")
void hasStock() {
    wireMock.stubFor(get(urlEqualTo("/order/7"))
            .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                    {
                      "id": 7,
                      "status": "Confirmed",
                      "total": 42.0
                    }
                    """)));
}

@State("Order Sku-786 does not exist")
void orderNotFound() {
    wireMock.stubFor(get(urlEqualTo("/order/786"))
            .willReturn(aResponse()
                    .withStatus(404)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                    {
                      "message": "Order not found"
                    }
                    """)));
}

@State("Provider can create orders")
void createOrder() {
    wireMock.stubFor(post(urlEqualTo("/order"))
            .willReturn(aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                    {
                      "statusCode": 201,
                      "orderId": 786,
                      "status": "CREATED",
                      "total": 42.0
                    }
                    """)));
}

@State("Order 786 can be updated")
void updateOrderState() {

    wireMock.stubFor(
            request("PUT", urlEqualTo("/order/786"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                                    {
                                      "id":786,
                                      "status":"SHIPPED",
                                      "total":42.0
                                    }
                                    """)));
}
@State("Order 786 status can be patched")
void patchOrderState() {

    wireMock.stubFor(
            request("PATCH", urlEqualTo("/order/786"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                                    {
                                      "id":786,
                                      "status":"DELIVERED",
                                      "total":42.0
                                    }
                                    """)));
}

@State("Order 786 can be deleted")
void deleteOrderState() {

    wireMock.stubFor(
            request("DELETE", urlEqualTo("/order/786"))
                    .willReturn(aResponse()
                            .withStatus(204)));
}

}