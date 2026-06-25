package tests;

import base.BaseTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import specs.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class DeleteTests extends BaseTest {

    @Test
    @DisplayName("Delete Posts")
    public void deletePosts() {

        given()
                .spec(requestSpec)
                .when()
                .delete("posts/1")

                .then()
                .spec(ResponseSpecs.success200());

    }
}