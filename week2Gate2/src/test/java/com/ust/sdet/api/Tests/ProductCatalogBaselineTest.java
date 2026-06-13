package com.ust.sdet.api.Tests;
import com.ust.sdet.api.ApiFlow.Factory.SpecFactory;
import com.ust.sdet.api.ApiFlow.Config.ApiConfig;
import com.ust.sdet.api.ApiFlow.Models.Orders;
import com.ust.sdet.api.ApiFlow.Models.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

class ProductCatalogBaselineTest {
    private static RequestSpecification reqSpec;
    private static ResponseSpecification respSpec;

    private static final String BASE_URL = ApiConfig.BASE_URL;

    private static ResponseSpecification created201(){
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

        reqSpec = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .build();

        respSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        String token = SpecFactory.getOAuthToken();
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
    @DisplayName("GET /products/{id} returns a specific product")
    void getProductById() {

        Product pro = given()

                .when()
                .get(SpecFactory.productById(101))

                .then()
                .spec(respSpec)
                .log().body()
                .body(matchesJsonSchemaInClasspath("schemas/json/Product.schema.json"))
                .extract()
                .as(Product.class);
        assertEquals(101, pro.id());
        assertEquals("Running Shoes", pro.name());
        assertEquals("Footwear", pro.category());
        assertEquals(4499, pro.price());
        assertTrue(pro.stock() >= 0);

    }

    @Test
    @DisplayName("Query Parameter")
    void test_query_param() {
        given()
                .queryParam("category", "Fitness")
                .queryParam("price", 1499)
                .when()

                .get(SpecFactory.productsPath())

                .then()
                .spec(respSpec)
                .body("items.category", everyItem(equalTo("Fitness")))
                .body("items[0].name", containsString("Insulated Water Bottle"))
                .time(lessThan(2000L));
    }
    @Test
    @DisplayName("get product details of 101")
    void should_get_product_details() {

        given()
                .spec(reqSpec)
                .when()
                .get("/products/101")
                .then()
                .spec(respSpec)
                .body("id", equalTo(101))
                .body("name", notNullValue())
                .body("name", equalTo("Running Shoes"))
                .body("price", greaterThan(3000));
    }

    @Test
    @DisplayName("Customer can login and adding to cart")
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
                .body(matchesJsonSchemaInClasspath("schemas/json/ProductList.schema.json"))
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


        Orders ord = given()
                .spec(SpecFactory.opsWrite())
                .body(order)

                .when()
                .post(SpecFactory.ordersPath())

                .then()
                .body("paymentMethod",equalTo("Credit card"))
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/json/OrdersSchema/Customer.schema.json"))
                .extract()
                .as(Orders.class);
        assertEquals("Confirmed",ord.status());
        assertEquals("Paid",ord.payment());

    }
    @Test
    @DisplayName("get details of 102")
    void get_product_details_() {

        given()
                .spec(reqSpec)
                .when()
                .get("/products/102")
                .then()
                .spec(respSpec)
                .body("id", equalTo(102))
                .body("name", equalTo("Travel Backpack"))
                .body("price", greaterThan(3000));
    }


    @Test
    @DisplayName("Customer can login and add Traval Backpck to cart")
    void customer_can_add_Travel_Backpack_to_cart() {
        var login = Map.of(
                "email", "customer@example.com",
                "password", "Password@123"
        );

        var item = Map.of(
                "productId", 102,
                "quantity", 2,
                "size", "30 L",
                "color", "Forest",
                "fulfilment", "Store pickup"
        );

        var order = Map.of(
                "paymentMethod", "UPI",
                "deliverySlot", "Tomorrow 2 PM - 5 PM",
                "address", "UST Campus, Bengaluru"
        );


        given()
                .spec(SpecFactory.opsRead())

                .when()
                .get(SpecFactory.productsPath())

                .then()
                .spec(SpecFactory.ok200())
                .body(matchesJsonSchemaInClasspath("schemas/json/ProductList.schema.json"))
                .body("items.size()", greaterThan(0))
                .body("items[1].name", equalTo("Travel Backpack"))
                .body("items[1].stock", equalTo(11))
                .body("items[1].category", equalTo("Bags"));


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
                .statusCode(anyOf(equalTo(200), equalTo(201)))
                .body("productId", equalTo(102))
                .body("product.price", equalTo(3299));


        Orders ord = given()
                .spec(SpecFactory.opsWrite())
                .body(order)

                .when()
                .post(SpecFactory.ordersPath())

                .then()
                .body("paymentMethod", equalTo("UPI"))
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/json/OrdersSchema/Customer.schema.json"))
                .extract()
                .as(Orders.class);
        assertEquals("Confirmed", ord.status());
        assertEquals("Paid", ord.payment());
    }

}
