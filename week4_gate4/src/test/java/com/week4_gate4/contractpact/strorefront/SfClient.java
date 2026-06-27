package com.week4_gate4.contractpact.strorefront;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

final class SfClient
{
    private final String baseUrl;

    SfClient(String url) {

        this.baseUrl = url;

    }
    Order getOrder(int id)
    {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .when()
                        .get("/product/" + id);

        return new Order(

                response.statusCode(),
                ((Number) response.path("id")).intValue(),
                response.path("status"),
                ((Number) response.path("total")).doubleValue()
        );
    }
    Order getInventory(int id)
    {
        Response response =
                given()

                        .baseUri(baseUrl)
                        .when()
                        .get("/cart/" + id);

        return new Order(
                response.statusCode(),
                ((Number) response.path("id")).intValue(),
                response.path("status"),
                ((Number) response.path("total")).doubleValue()



        );

    }

    CreateOrder createOrder(
            String sku,
            int quantity
    ){
        String jsonBody =
                """
                {
                 
                  "sku":"%s",
                  "quantity":%d,
                
          
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
                        .post("/items/106");

        return new CreateOrder(

                response.statusCode(),

                response.path("sku"),

                response.path("quantity")


        );

    }


    record Order(
            int statuscode,
            int orderId,
            String status,
            double total
    ){}
        record CreateOrder(
                int statuscode,
                String sku,
                int quantity
        ){}
}