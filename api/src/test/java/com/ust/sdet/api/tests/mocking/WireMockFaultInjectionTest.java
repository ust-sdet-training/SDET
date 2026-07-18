package com.ust.sdet.api.tests.mocking;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WireMockFaultInjectionTest {

    @Test
    public void wiremockCanMockFaultInjectableDependency() throws Exception {
        WireMockServer wireMockServer = new WireMockServer(18089);
        wireMockServer.start();

        try {
            wireMockServer.stubFor(get(urlEqualTo("/payment/charge"))
                    .willReturn(aResponse()
                            .withStatus(502)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"error\":\"GATEWAY_ERROR\"}")));

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:18089/payment/charge"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(502, response.statusCode());
            assertEquals("{\"error\":\"GATEWAY_ERROR\"}", response.body());
        } finally {
            wireMockServer.stop();
        }
    }
}
