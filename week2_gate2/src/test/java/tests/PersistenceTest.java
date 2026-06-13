package tests;

import io.restassured.response.Response;
import models.OrderRow;
import org.junit.jupiter.api.Test;

import static base.BaseTest.database;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersistenceTest {
    @Test
    void createOrder_isPersisted() throws Exception {

        String token = "YOUR_ACCESS_TOKEN";

        String newOrder = """
        {
          "items":[101]
        }
        """;

        Response response =
                given()
                        .baseUri("http://localhost:4000/api/secure/orders")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .body(newOrder)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        long id = response.path("id");

        OrderRow order = database.findOrder(id);

        assertNotNull(order);
        assertEquals(id, order.id());
    }
}
