package tests;

import utils.BaseTest;
import io.restassured.http.ContentType;
import models.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.SpecFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;

public class StoreApiTest extends BaseTest {

    @Test
    @DisplayName("Placing the Order and getting the order by ID")
    void placeOrderAndGetOrder() {

        Map<String, Object> orderBody = Map.of(
                "id", 1,
                "petId", 1001,
                "quantity", 2,
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

        assertEquals(2, createdOrder.quantity());

        Long orderId = createdOrder.id();

        OrderResponse fetchedOrder =
                given()
                        .pathParam("orderId", orderId)

                        .when()
                        .get(SpecFactory.orderByIdURL())

                        .then()
                        .time(lessThan(8000L))
                        .spec(SpecFactory.successResponseSpec())
                        .extract()
                        .as(OrderResponse.class);

        assertEquals(orderId, fetchedOrder.id());
        assertEquals("placed", fetchedOrder.status());
    }
}