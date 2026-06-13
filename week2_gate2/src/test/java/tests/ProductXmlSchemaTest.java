package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

public class ProductXmlSchemaTest {

    @Test
    @DisplayName("Product XML response should match XSD schema")
    void productXmlShouldMatchSchema() {

        given()
                .accept("application/xml")
                .when()
                .get("/api/products/101")
                .then()
                .statusCode(200)
                .body(
                        matchesXsdInClasspath(
                                "schemas/product.xsd"
                        )
                );
    }
}