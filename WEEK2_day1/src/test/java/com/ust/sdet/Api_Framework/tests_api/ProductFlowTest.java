package com.ust.sdet.Api_Framework.tests_api;

import com.ust.sdet.Api_Framework.model.ProductModel;
import com.ust.sdet.Api_Framework.specs.SpecFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


import java.util.Map;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.*;

public class ProductFlowTest {

    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    private static final String BASE_URL = System.getProperty(
            "baseUrl",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000")
    );

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

        requestSpec = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(anyOf(equalTo(200), equalTo(401), equalTo(204)))
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(1000L))
                .build();

        String token = SpecFactory.getOAuthToken();
//        authed = SpecFactory.authed(token);
    }

    @Test
    @DisplayName("Product record class tests")
    void with_prod_rec() {
        ProductModel product = given().
                pathParam("id", 101)
                .spec(requestSpec)
                .when()
                .get("/products/{id}")
                .then()
                .spec(responseSpec)
                .extract()
                .as(ProductModel.class);

        assertEquals(101, product.id());
        assertEquals(18, product.stock());
        assertEquals("Running Shoes", product.name());
    }


    //My attempt
    @Test
    @DisplayName("Login to Order flow  testing")
    void login_getProducts_createOrder(){

        var user = Map.of("username","customer@example.com",
                "password","Password@123");

        var item_added = Map.of(
                "productId",101,
                "quantity",1,
                "size","UK 7",
                "color","Navy",
                "fulfilment","Home delivery"
        );


        var order = Map.of(
                "paymentMethod", "Credit card",
                "deliverySlot", "Tomorrow 9 AM - 12 PM",
                "address", "UST Campus, Bengaluru"
        );



//    Login and get token

        String token =
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec)
                        .extract()
                        .path("token");

        given()
                .spec(requestSpec)
                .header("Authorization","Bearer " + token)
                .when()
                .get("/products")
                .then()
                .spec(responseSpec)
                .body("items[0].name",equalTo("Running Shoes"));


        given()
                .spec(requestSpec)
                .body(item_added)
                .header("Authorization","Bearer " + token)
                .when()
                .post("/cart/items")
                .then()
                .spec(responseSpec)
                .log().all();


        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/cart")
                .then()
                .spec(responseSpec);

        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/cart")
                .then()
                .spec(responseSpec)
//               .body("items[0].product.name",equalTo("Running Shoes"))
                .body(matchesJsonSchemaInClasspath("schema/json/product.schema.json"));


        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/orders")
                .then()
                .spec(responseSpec)
                .body(matchesJsonSchemaInClasspath("schema/json/orders_schema/partner-orders.schema.json"));
    }


}