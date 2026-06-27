package com.ust.sdet.provider;

import au.com.dius.pact.provider.junitsupport.State;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryState {

    @State("SKU-9 has Stock")
    public void inventoryState() {

        stubFor(get(urlEqualTo("/Inventory/SKU-9"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {"statusCode":200,"sku":"SKU-9","quantity":20}
                        """)));
    }
}
