package com.ust.sdet.Api_Framework.tests_api;


import com.ust.sdet.Api_Framework.config.ApiConfig;
import com.ust.sdet.Api_Framework.model.OrderModel;
import com.ust.sdet.Api_Framework.specs.SpecFactory;
import com.ust.sdet.dbframework.config.DatabaseConfig;
import com.ust.sdet.dbframework.support.DbSupport;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.ust.sdet.Api_Framework.specs.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpsFlow {


    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    @DisplayName("Health API should return service status")
    void verifyHealthEndpoint() {

        given()
                .spec(SpecFactory.readSpec())

                .when()
                .get(SpecFactory.healthPath())

                .then()
                .spec(SpecFactory.ok200())
                .body("status", equalTo("ok"))
                .body("service", equalTo("sdet-retail-app"))
                .body("authDemo", equalTo("w2d4"));
    }

    @Test
    @DisplayName("xml test")
    void xml_test() {

        given()
                .accept("application/xml")

                .when()
                .get(SpecFactory.legacyProductXmlPath(101))

                .then()
                .contentType("application/xml")
                .body(matchesXsdInClasspath("schemas/xsd/product.xsd"));
    }


    @Test
    @DisplayName("Customer can login and add Running Shoes to cart")
    void customer_can_add_running_shoes_to_cart() {
        var login = Map.of(
                "email","customer@example.com",
                "password","Password@123"
        );

        var item = Map.of(
                "productId",101,
                "quantity", 1,
                "size", "UK 7",
                "color", "Navy",
                "fulfilment", "Home delivery"
        );

        var order = Map.of(
                "paymentMethod", "Credit card",
                "deliverySlot", "Tomorrow 9 AM - 12 PM",
                "address", "UST Campus, Bengaluru"
        );


        given()
                .spec(SpecFactory.opsRead())

                .when()
                .get(SpecFactory.productsPath())

                .then()
                .spec(SpecFactory.ok200())
                .body(matchesJsonSchemaInClasspath("schema/json/product-list.schema.json"))
                .body("items.size()", greaterThan(0))
                .body("items[0].name",equalTo("Running Shoes"))
                .body("items[0].stock",equalTo(18))
                .body("items[0].category", equalTo("Footwear"));



        given()
                .spec(SpecFactory.opsRead())

                .when()
                .delete(SpecFactory.cartPath())

                .then()

                .spec(SpecFactory.noContent204());

        given()
                .spec(SpecFactory.opsRead())
                .contentType(ContentType.JSON)
                .body(item)

                .when()
                .post(SpecFactory.cartItemsPath())

                .then()
                .log().all()
                .statusCode(anyOf(equalTo(200),equalTo(201)))
                .body("productId", equalTo(101))
                .body("product.price",equalTo(4499));


        OrderModel ord = given()
                .spec(SpecFactory.opsWrite())
                .body(order)

                .when()
                .post(SpecFactory.ordersPath())

                .then()
                .body("paymentMethod",equalTo("Credit card"))
                .log().all()
                .body(matchesJsonSchemaInClasspath("schema/json/orders_schema/customer-orders.schema.json"))
                .extract()
                .as(OrderModel.class);
        assertEquals("Confirmed",ord.status());
        assertEquals("Paid",ord.payment());

    }

    public static class DataTesting {
        private static DbSupport database;

        @BeforeAll
        static void setup() {

            DatabaseConfig config = new DatabaseConfig(
                    "jdbc:mysql://localhost:3306/order_db",
                    "root",
                    "Salmon@123"
            );

            database = new DbSupport(config);
        }


        @Test
        @DisplayName("Local M1: MySQL is Reachable through JDBC")
        void localMySqlIsReachable() throws Exception {
            assertTrue(database.isReachable());
        }

    }

    //@ExtendWith(LocalBackendServer.class)
    public static class SchemaTesting {

        private static RequestSpecification requestSpec;
        private static ResponseSpecification responseSpec;
        private static RequestSpecification xmlRequestSpec;
        private static ResponseSpecification xmlResponseSpec;

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
                    .build();

            responseSpec = new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(lessThan(1200L))
                    .build();

            xmlRequestSpec = new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setBasePath("/api")
                    .setAccept(ContentType.XML)
                    .setContentType(ContentType.XML)
                    .build();
            xmlResponseSpec = new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectContentType(ContentType.XML)
                    .expectResponseTime(lessThan(1000L))
                    .build();

        }

        @Test
        @DisplayName("M1: Product details matches with json")
        void productDetail_matchersJsonSchema(){
            given().spec(requestSpec)
                    .pathParam("id",101)
                    .when()
                    .get(productById(101))
                    .then()
                    .spec(responseSpec)
                    .body(matchesJsonSchemaInClasspath("schema/json/product.schema.json"));

        }

        @Test
        @DisplayName("M2: Product List details matches with json")
        void product_list_Detail_matchersJsonSchema(){
            given().spec(requestSpec)
                    .when()
                    .get(productsPath())
                    .then()
                    .spec(responseSpec)
                    .body(matchesJsonSchemaInClasspath("schema/json/product-list.schema.json"));
        }

        @Test
        @DisplayName("M3: Legacy Product XML matches product XSD")
        void productXml_validation(){
            given()
                    .spec(xmlRequestSpec)
                    .when()
                    .get(legacyProductXmlPath(101))
                    .then()
                    .spec(xmlResponseSpec)
                    .body(matchesXsdInClasspath("schema/xsd/product.xsd"));

        }
    }
}
