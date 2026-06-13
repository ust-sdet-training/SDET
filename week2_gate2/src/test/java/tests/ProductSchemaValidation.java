package tests;

import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static specs.SpecFactory.authSpec;

public class ProductSchemaValidation {

    @Test
    void validateProductsSchema() {

        given()
                .spec(SpecFactory.authSpec())

                .when()
                .get("/api/products")

                .then()
                .spec(SpecFactory.okJson())
                .body(matchesJsonSchemaInClasspath(
                        "schemas/json/product-list.schema.json"));
    }
}
