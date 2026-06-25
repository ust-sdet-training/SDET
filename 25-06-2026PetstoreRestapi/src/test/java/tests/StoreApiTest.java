package tests;

import base.BaseTest;
import model.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.SpecFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class StoreApiTest extends BaseTest {

    @Test
    @DisplayName("Place the order and get the order by id")
    void placeOrderAndGetOrder() {

        Map<String, Object> orderBody = Map.of(
                "id", 1,
                "petId", 101,
                "quantity", 1,
                "status", "placed",
                "complete", true
        );

        OrderResponse createdOrder =
                given()
                        .spec(SpecFactory.reqSpec())
                        .body(orderBody)

                        .when()
                        .post(SpecFactory.orderURL())

                        .then()
                        .spec(SpecFactory.successResponseSpec())
                        .extract()
                        .as(OrderResponse.class);

        assertEquals(1, createdOrder.quantity());

        Long orderId = createdOrder.id();

        OrderResponse fetchedOrder =
                given()
                        .pathParam("orderId", orderId)

                        .when()
                        .get(SpecFactory.orderByIdURL())

                        .then()
                        .spec(SpecFactory.successResponseSpec())
                        .extract()
                        .as(OrderResponse.class);

        assertEquals(orderId, fetchedOrder.id());
        assertEquals("placed", fetchedOrder.status());
    }
}