package com.ust.sdet.provider;


import au.com.dius.pact.provider.junitsupport.State;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CreateOrderState {

    @State("Creating a new order")
    public void createOrder() {

        stubFor(post(urlEqualTo("/orders/"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {"statusCode":201,"orderId":101,"status":"CREATED","total":2000.0}
                        """)));
    }
}
