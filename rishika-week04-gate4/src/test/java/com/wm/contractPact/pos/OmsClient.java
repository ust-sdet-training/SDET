package com.wm.contractPact.pos;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
public class OmsClient {
    private final String baseUrl;

    public OmsClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Order getOrder(int orderId) {
        return given()
                .baseUri(baseUrl)
                .when()
                .get("/order/" + orderId)
                .then()
                .statusCode(200)
                .extract()
                .as(Order.class);
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/order")
                .then()
                .statusCode(201)
                .extract()
                .as(CreateOrderResponse.class);
    }

    public record Order(
            int orderId,
            String customerName,
            String status,
            double amount
    ) {}

    public record CreateOrderRequest(
            String customerName,
            String product,
            int quantity
    ) {}

    public record CreateOrderResponse(
            int orderId,
            String status,
            double amount
    ) {}
}