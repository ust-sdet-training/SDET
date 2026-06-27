package provider;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class FoodProviderServer {

    private static final int PORT = 4010;
    private static WireMockServer server;

    public static void startServer() {

        if (server != null && server.isRunning()) {
            return;
        }

        server = new WireMockServer(PORT);
        server.start();

        configureFor("localhost", PORT);

        stubGetFoodOrder();
        stubGetRestaurant();
        stubCreateFoodOrder();

        System.out.println("Food Provider started on port " + PORT);
    }

    private static void stubGetFoodOrder() {

        server.stubFor(get(urlEqualTo("/foodorder/101"))
                .willReturn(okJson("""
                        {
                          "statusCode": 200,
                          "orderId": 101,
                          "restaurantName": "Pizza Hub",
                          "foodItem": "Veg Pizza",
                          "quantity": 2,
                          "totalAmount": 598.0,
                          "orderStatus": "CONFIRMED"
                        }
                        """)));
    }

    private static void stubGetRestaurant() {

        server.stubFor(get(urlEqualTo("/restaurant/101"))
                .willReturn(okJson("""
                        {
                          "restaurantId": 101,
                          "restaurantName": "Pizza Hub",
                          "city": "Chennai",
                          "open": true
                        }
                        """)));
    }

    private static void stubCreateFoodOrder() {

        server.stubFor(post(urlEqualTo("/foodorder"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "statusCode": 201,
                                  "orderId": 101,
                                  "restaurantName": "Pizza Hub",
                                  "foodItem": "Veg Pizza",
                                  "quantity": 2,
                                  "totalAmount": 598.0,
                                  "orderStatus": "CONFIRMED"
                                }
                                """)));
    }

    public static void stopServer() {

        if (server != null && server.isRunning()) {
            server.stop();
        }
    }
}