package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import static io.restassured.RestAssured.given;

public class ProductXml {

    @Test
    @DisplayName("legacy product XML matches product XSD")
    void productXml_matchesXsd(){
        given()
                .spec(SpecFactory.legacyProductsXml())
                .when()
                .get("/101.xml")
                .then()
                .log().ifValidationFails()
                .statusCode(200);

    }
}
