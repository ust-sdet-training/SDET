package provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import config.PactProviderConfig;
import state.CatalogProviderStates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

@Provider("catalog-service")
@PactBroker(
        url = "http://127.0.0.1:9292"
)
public class CatalogProviderVerificationTest {

    @RegisterExtension
    static final WireMockExtension wireMock = PactProviderConfig.WIRE_MOCK;
    private final CatalogProviderStates states = new CatalogProviderStates(wireMock);

    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("127.0.0.1", 8081)
        );
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("SKU-1 exists")
    void skuExists() {
        states.skuExists();
    }

    @State("SKU-404 missing")
    void skuMissing() {
        states.skuMissing();
    }
}