package wiremock.server;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class server {

    private static final WireMockServer server = new WireMockServer(4010);

    public static void start() {

        if (!server.isRunning()) {
            server.start();
        }

        configureFor("localhost", 4010);

        server.stubFor(get(urlEqualTo("/orders/1"))
                .willReturn(okJson("""
                    {
                      "id":1,
                      "status":"CONFIRMED"
                    }
                """)));

        server.stubFor(post(urlEqualTo("/orders"))
                .willReturn(created()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                              "id":100,
                              "status":"CREATED"
                            }
                        """)));

        server.stubFor(get(urlEqualTo("/inventory/SKU-9"))
                .willReturn(okJson("""
                    {
                      "sku":"SKU-9",
                      "stock":50
                    }
                """)));
    }

    public static void stop() {
        server.stop();
    }
}