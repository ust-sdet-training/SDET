package ust.gama.sdet.Gate4.PactContract.contracts.catalog;


import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.HttpsTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerConsumerVersionSelectors;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.loader.SelectorBuilder;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Provider("catalog-service")
@PactBroker(url = "http://127.0.0.1:9292")

@PactFolder("target/pacts")
public class catalogServiceVerification {
    @RegisterExtension
    private static final WireMockExtension wiremock =
            WireMockExtension.newInstance()
                    .options(wireMockConfig().port(4030))
                    .build();

    @PactBrokerConsumerVersionSelectors
    public static SelectorBuilder consumerVersionSelector(){
        return new SelectorBuilder().mainBranch().deployedOrReleased();
    }

    @BeforeEach
    void setup(PactVerificationContext context){
        context.setTarget(new HttpTestTarget("127.0.0.1", 4030));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(PactVerificationContext context){
        context.verifyInteraction();
    }

    @State("order ID 84 exists")
    void catalogHasOrder(){
        wiremock.stubFor(get(urlEqualTo("/catalog/sku/84"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"orderId": 84,"product": "TravelBag", "status": "CONFIRMED",
                                 "statusCode": 200, "totalPrice": 93.0}
                                """)));
    }

    @State("Catalog Service can create Orders")
    void catalogCreatesOrder(){
        wiremock.stubFor(post(urlEqualTo("/catalog/orders"))
                .withHeader("Content-Type", matching("application/json(;.*)?"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"orderId": 104,"product": "YogaMat", "status": "CREATED",
                                "statusCode": 201, "totalPrice": 240.0}
                                """)));
    }

    @State("Catalog Service is down")
    void catalogServiceDownJustWhenRequest(){
        wiremock.stubFor(get(urlEqualTo("/catalog/orders/84"))
                .willReturn(aResponse()
                .withStatus(503)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                {
                  "reason": "SERVICE_IS_DOWN",
                  "message": "Catalog service is down temporarily. Sorry for the delay, and thank you for your patience!"
                }
                """)));

    }

    @State("Order Id 999 does not exist")
    void catalogIsRequestedForNonExistentOrder(){
        wiremock.stubFor(get(urlEqualTo("/catalog/sku/999"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                               {"error": "ORDER_NOT_FOUND",
                               "message": "Order not Found"}
                               """)));
    }
}
