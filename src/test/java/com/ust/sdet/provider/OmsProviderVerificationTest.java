package com.ust.sdet.provider;


import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Tag("provider")
@Provider("oms-provider")
@PactBroker
@ExtendWith({
        PactVerificationInvocationContextProvider.class
})
//@PactFolder("target/pacts")
public class OmsProviderVerificationTest {

    @RegisterExtension
    static WireMockExtension wireMock =
            WireMockExtension.newInstance()
                    .options(wireMockConfig().port(4010))
                    .build();

    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 4010));
        WireMock.configureFor("localhost", 4010);
    }


    CreateOrderState createOrderState = new CreateOrderState();

    @State("Creating a new order")
    void createOrder() {
        createOrderState.createOrder();
    }
    GetOrderState getOrderState = new GetOrderState();

    @State("Order 12 exists")
    void getOrder() {
        getOrderState.getOrder();
    }

    InventoryState inventoryState = new InventoryState();

    @State("SKU-9 has Stock")
    void inventoryState() {
        inventoryState.inventoryState();
    }



    @TestTemplate
    void verify(PactVerificationContext context) {

        context.verifyInteraction();
    }
}
