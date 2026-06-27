package com.ust.sdet.wiremock.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;

import com.ust.sdet.wiremock.dummyserver.Server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;


@Provider("Product")
@PactBroker(
        url = "https://jjjj-73df0332.pactflow.io",
        authentication = @PactBrokerAuth(
                token = "Mxt5U4qwYAmkJRK0hNY3wQ"
        )

)

public class ProviderVerificationTest {

    @BeforeAll
    static void start() {
        Server.start();
    }

    @AfterAll
    static void stop() {
        Server.stop();
    }

    @BeforeEach
    void target(PactVerificationContext context) {

        context.setTarget(
                new HttpTestTarget(
                        "localhost",
                        4010
                )
        );
    }

    @TestTemplate
    @ExtendWith(
            PactVerificationInvocationContextProvider.class)
    void verify(
            PactVerificationContext context) {

        context.verifyInteraction();
    }

    @State("product exists")
    void productExists() {

    }
}