package com.ust.sdet.wiremock.dummyserver;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class Server {

    private static final WireMockServer server =
            new WireMockServer(options().port(4010));

    public static void start() {

        server.start();

        configureFor("localhost", 4010);

        stubProduct();

        System.out.println("WireMock Started");
    }

    public static void stop() {

        server.stop();

        System.out.println("WireMock Stopped");
    }

    private static void stubProduct() {

        server.stubFor(
                get(urlEqualTo("/products/101"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json")
                                        .withBody("""
        {
          "id":101,
          "name":"Travel Backpack",
          "price":89,
          "stock":"In Stock"
        }
        """)
                        )
        );
    }
}