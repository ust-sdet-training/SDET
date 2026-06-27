package com.ust.sdet.provider;

import au.com.dius.pact.provider.junitsupport.State;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class GetOrderState {

        @State("Order 12 exists")
        public void getOrder() {

        stubFor(get(urlEqualTo("/orders/12"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"orderId":12,"status":"CONFIRMED","total":42.00}
                        """)));
    }


}
