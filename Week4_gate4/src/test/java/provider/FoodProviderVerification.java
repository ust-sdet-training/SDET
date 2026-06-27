package provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("food-provider")
@PactFolder("target/pacts")

public class FoodProviderVerification {

    @BeforeAll
    static void startProvider() {
        FoodProviderServer.startServer();
    }

    @AfterAll
    static void stopProvider() {
        FoodProviderServer.stopServer();
    }

    @BeforeEach
    void setup(PactVerificationContext context) {

        context.setTarget(new HttpTestTarget("localhost", 4010));

    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {

        context.verifyInteraction();

    }

    @State("Food order 101 exists")
    void foodOrderExists() {

        // WireMock already has this stub configured.

    }

    @State("Restaurant Pizza Hub is available")
    void restaurantAvailable() {

        // WireMock already has this stub configured.

    }

    @State("Provider can create food orders")
    void providerCanCreateFoodOrders() {

        // WireMock already has this stub configured.

    }
}