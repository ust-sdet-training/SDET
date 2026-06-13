package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ProductTests extends BaseTest {


    @Test
    @DisplayName("Verify product can be fetched using valid product id")
    void verifyGetProductById() {

        given()
                .spec(RequestSpecs.basicRequestSpec)

                .when()
                .get("/api/products/101")

                .then()
                .statusCode(200)
                .body("id", equalTo(101))
                .body("name", equalTo("Running Shoes"))
                .body("category", equalTo("Footwear"))
                .body("price", greaterThan(0));
    }

    @Test
    @DisplayName("Verify invalid product id returns not found")
    void verifyInvalidProductId() {

        given()
                .spec(RequestSpecs.basicRequestSpec)

                .when()
                .get("/api/products/99999")

                .then()
                .spec(ResponseSpecs.notFoundSpec)
                .body("message",
                        equalTo("Product not found"));
    }

    @Test
    @DisplayName("Verify products can be searched")
    void verifySearchProducts() {

        Response response =

                given()
                        .spec(RequestSpecs.basicRequestSpec)
                        .queryParam("search",
                                "shoe")

                        .when()
                        .get("/api/products");

        response.then()
                .statusCode(200)
                .body("total",
                        greaterThan(0));

        response.then()
                .body("items.name",
                        hasItem("Running Shoes"));
    }

    @Test
    @DisplayName("Verify products can be filtered by category")
    void verifyFilterProductsByCategory() {

        given()
                .spec(RequestSpecs.basicRequestSpec)
                .queryParam("category",
                        "Fitness")

                .when()
                .get("/api/products")

                .then()
                .statusCode(200)
                .body("total",
                        greaterThan(0))
                .body("items.category",
                        everyItem(equalTo("Fitness")));
    }
    @Test
    void verifyProductsSchema() {

        given()
                .spec(RequestSpecs.basicRequestSpec)

                .when()
                .get("/api/products")

                .then()
                .statusCode(200)

                .body(
                        matchesJsonSchemaInClasspath(
                                "schemas/products-schema.json"));
    }
}