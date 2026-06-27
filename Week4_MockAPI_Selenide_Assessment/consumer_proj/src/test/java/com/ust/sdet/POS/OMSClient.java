package com.ust.sdet.POS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;

import java.net.http.HttpClient;

import static io.restassured.RestAssured.given;

final class OMSClient {


    private String baseUrl;
    private HttpClient client;

    private static final String BASE_URL = System.getProperty(
            "baseUrl",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4010/")
    );

    public OMSClient (String baseurl) {
        this.baseUrl = baseurl;
    }


    @BeforeEach
    void setup() {
        this.baseUrl = BASE_URL;
        this.client = HttpClient.newHttpClient();
    }


    public Order getOrder(int id) {

        Response response = given()
                .baseUri(baseUrl)
                .basePath("/orders/" + id)
                .get();

        response.then().statusCode(200);


        int orderId = response.then().extract().path("id");
        String status = response.then().extract().path("status");

        return new Order(orderId,status);
    }

    public Order createOrder(Order order){
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/orders")
                .then()
                .statusCode(201)
                .extract()
                .as(Order.class);
    }



    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Order(int orderId,String status){}


}
