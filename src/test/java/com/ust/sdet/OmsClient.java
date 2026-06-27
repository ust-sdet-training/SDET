package com.ust.sdet;


import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
public final class OmsClient {


    private final String baseUrl;


    public OmsClient(String url) {

        this.baseUrl = url;

    }

    public Order getOrder(int id){

        Response response =
                given()
                        .baseUri(baseUrl)
                        .when()
                        .get("/orders/" + id);
        return new Order(

                response.statusCode(),

                ((Number) response.path("orderId")).intValue(),

                response.path("status"),

                ((Number) response.path("total")).doubleValue()

        );

    }

    public Order getinvalidOrder(int id){

        Response response =
                given()
                        .baseUri(baseUrl)
                        .when()
                        .post("/orders/" + id);
        return null;

    }

    public Order createOrder(
            String sku,
            int quantity
    ){


        String jsonBody =
                """
                {
                  "sku":"%s",
                  "quantity":%d
                }
                """.formatted(
                        sku,
                        quantity
                );



        Response response =
                given()
                        .baseUri(baseUrl)
                        .contentType("application/json")
                        .body(jsonBody)

                        .when()
                        .post("/orders/");




        return new Order(
                response.statusCode(),
                response.path("orderId"),
                response.path("status"),
                response.jsonPath().getDouble("total")

        );


    }

    public Inventory getInventory(String sku) {
        Response response = given()
                .baseUri(baseUrl)
                .when()
                .get("/Inventory/" + sku);

        return new Inventory(
                response.statusCode(),
                response.path("sku"),
                ((Number) response.path("quantity")).intValue()
        );
    }

    public record Order(
            int statuscode,
            int orderId,
            String status,
            double total
    ){}


    public record Inventory(
            int statuscode,
            String sku,
            int quantity
    ){}
}