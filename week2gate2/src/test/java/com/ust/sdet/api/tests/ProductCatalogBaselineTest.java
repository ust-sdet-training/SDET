package com.ust.sdet.api.tests;
import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import com.ust.sdet.api.API_FrameWork.models.Orders;
import com.ust.sdet.api.API_FrameWork.models.Product;
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
    private static RequestSpecification authed;


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
                .body(matchesJsonSchemaInClasspath("schemas/json/product.schema.json"))
                .extract()
                .as(Product.class);
        assertEquals(101, pro.id());
        assertEquals("Running Shoes", pro.name());
        assertEquals("Footwear", pro.category());
        assertEquals(4499, pro.price());
        assertTrue(pro.stock() >= 0);

    }
    @Test
    @DisplayName("Product name verification")
    void has_product(){

        given()
                .log().ifValidationFails()

                .when()
                .get(SpecFactory.productsPath())

                .then()
                .log().ifValidationFails()
                .spec(respSpec)
                .body("items[0].name",equalTo("Running Shoes"))
                .body("items[0].price", equalTo(4499));
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
                .body(matchesJsonSchemaInClasspath("schemas/json/productList.schema.json"))
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
                .body(matchesJsonSchemaInClasspath("schemas/json/orders_schema/customer-orders.schema.json"))
                .extract()
                .as(Orders.class);
        assertEquals("Confirmed",ord.status());
        assertEquals("Paid",ord.payment());

    }





}
