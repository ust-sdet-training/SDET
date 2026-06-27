package consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(
        providerName = "food-provider",
        pactVersion = PactSpecVersion.V4
)
public class FoodConsumerPactTest {

    @Pact(provider = "food-provider", consumer = "restaurant-service")
    V4Pact getFoodOrderContract(PactDslWithProvider builder) {

        return builder
                .given("Food order 101 exists")
                .uponReceiving("a request for food order 101")
                .path("/foodorder/101")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .integerType("statusCode", 200)
                        .integerType("orderId", 101)
                        .stringType("restaurantName", "Pizza Hub")
                        .stringType("foodItem", "Veg Pizza")
                        .integerType("quantity", 2)
                        .numberType("totalAmount", 598.0)
                        .stringType("orderStatus", "CONFIRMED"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getFoodOrderContract")
    void shouldFetchExistingFoodOrder(MockServer mockServer) {

        FoodDeliveryClient client = new FoodDeliveryClient(mockServer.getUrl());

        FoodDeliveryClient.FoodOrder order = client.getFoodOrder(101);

        assertEquals(101, order.orderId());
        assertEquals("Pizza Hub", order.restaurantName());
        assertEquals("CONFIRMED", order.orderStatus());
    }

    @Pact(provider = "food-provider", consumer = "restaurant-service")
    V4Pact getRestaurantContract(PactDslWithProvider builder) {

        return builder
                .given("Restaurant Pizza Hub is available")
                .uponReceiving("a request for restaurant details")
                .path("/restaurant/101")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .integerType("restaurantId", 101)
                        .stringType("restaurantName", "Pizza Hub")
                        .stringType("city", "Chennai")
                        .booleanType("open", true))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getRestaurantContract")
    void shouldFetchRestaurantDetails(MockServer mockServer) {

        Response response = given()
                .baseUri(mockServer.getUrl())
                .when()
                .get("/restaurant/101");

        response.then().statusCode(200);
    }

    @Pact(provider = "food-provider", consumer = "restaurant-service")
    V4Pact createFoodOrderContract(PactDslWithProvider builder) {

        return builder
                .given("Provider can create food orders")
                .uponReceiving("a request to create a food order")
                .path("/foodorder")
                .method("POST")
                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .integerType("statusCode", 0)
                        .integerType("orderId", 101)
                        .stringType("restaurantName", "Pizza Hub")
                        .stringType("foodItem", "Veg Pizza")
                        .integerType("quantity", 2)
                        .numberType("totalAmount", 598.0)
                        .stringType("orderStatus", "NEW"))
                .willRespondWith()
                .status(201)
                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json")
                .body(new PactDslJsonBody()
                        .integerType("statusCode", 201)
                        .integerType("orderId", 101)
                        .stringType("restaurantName", "Pizza Hub")
                        .stringType("foodItem", "Veg Pizza")
                        .integerType("quantity", 2)
                        .numberType("totalAmount", 598.0)
                        .stringType("orderStatus", "CONFIRMED"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "createFoodOrderContract")
    void shouldCreateFoodOrder(MockServer mockServer) {

        FoodDeliveryClient client = new FoodDeliveryClient(mockServer.getUrl());

        FoodDeliveryClient.FoodOrder request =
                new FoodDeliveryClient.FoodOrder(
                        0,
                        101,
                        "Pizza Hub",
                        "Veg Pizza",
                        2,
                        598.0,
                        "NEW");

        FoodDeliveryClient.FoodOrder response =
                client.createFoodOrder(request);

        assertEquals(101, response.orderId());
        assertEquals("CONFIRMED", response.orderStatus());
    }
}