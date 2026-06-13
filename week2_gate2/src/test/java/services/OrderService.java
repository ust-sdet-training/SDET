package services;

import io.restassured.response.Response;
import models.OrderRequest;
import specs.RequestSpecs;

import static io.restassured.RestAssured.given;

public class OrderService {

    public Response createOrder(OrderRequest request) {

        return given()

                .spec(RequestSpecs.authOrders())

                .body(request)

                .when()

                .post();
    }

    public Response allocateOrder(long id) {

        return given()

                .spec(RequestSpecs.authOrders())

                .when()

                .post("/" + id + "/allocate");
    }

    public Response shipOrder(long id) {

        return given()

                .spec(RequestSpecs.authOrders())

                .when()

                .post("/" + id + "/ship");
    }

    public Response fetchOrder(long id) {

        return given()

                .spec(RequestSpecs.authOrders())

                .when()

                .get("/" + id);
    }
}
