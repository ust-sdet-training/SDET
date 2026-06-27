package consumer;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class FoodDeliveryClient {

    private final String baseUrl;

    public FoodDeliveryClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public FoodOrder createFoodOrder(FoodOrder order) {

        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/foodorder")
                .then()
                .statusCode(201)
                .extract()
                .as(FoodOrder.class);
    }

    // Get an existing food order
    public FoodOrder getFoodOrder(int orderId) {

        Response response = given()
                .baseUri(baseUrl)
                .when()
                .get("/foodorder/" + orderId);

        response.then().statusCode(200);

        return response.as(FoodOrder.class);
    }

    public record FoodOrder(

            int statusCode,
            int orderId,
            String restaurantName,
            String foodItem,
            int quantity,
            double totalAmount,
            String orderStatus

    ) {
    }
}