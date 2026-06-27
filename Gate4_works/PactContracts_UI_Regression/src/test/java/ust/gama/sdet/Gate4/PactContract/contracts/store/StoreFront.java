package ust.gama.sdet.Gate4.PactContract.contracts.store;

import com.codeborne.selenide.conditions.Or;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

final class StoreFront {
    private String baseUrl;

    public StoreFront(String baseUrl){
        this.baseUrl=baseUrl;
    }

    public Order fetchOrder(int id){
        Response resp = given()
                .baseUri(baseUrl)
                .basePath("/catalog")
                .when()
                .get("/sku/"+id );
        resp.then().statusCode(200);

        int orderId = resp.then().extract().path("orderId");
        String product = resp.then().extract().path("product");
        String status = resp.then().extract().path("status");
        Number val = resp.then().extract().path("totalPrice");
        double total = val.doubleValue();

        return new Order(resp.statusCode(), orderId, product, status, total);
    }


    public Order createOrder(Order order){
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/catalog/orders")
                .then()
                .statusCode(201)
                .extract()
                .as(Order.class);
    }

    public ErrorMessage getNegOrder(int id) {

        Response response = given()
                .baseUri(baseUrl)
                .when()
                .get("/catalog/sku/"+id);

        response.then().statusCode(404);
        String error = response.then().extract().path("error");
        String message = response.then().extract().path("message");


        return new ErrorMessage(response.statusCode(), error, message);
    }

    public InfoMessage getInfoMessage(int id) {

        Response response = given()
                .baseUri(baseUrl)
                .when()
                .get("/catalog/orders/"+id);

        response.then().statusCode(503);
        String error = response.then().extract().path("reason");
        String message = response.then().extract().path("message");


        return new InfoMessage(response.statusCode(), error, message);
    }

    record Order(int statusCode, int orderId, String product, String status, double totalPrice){}

    record ErrorMessage(int statusCode, String error, String message){}

    record InfoMessage(int statusCode, String reason, String message){}

}
