package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import utils.PetPayload;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetCRUDTest extends BaseTest {

    static long petId = 999999;

    @Test
    @Order(1)
    void createPet() {

        given()
                .body(PetPayload.createPet(petId))
                .when()
                .post("/pet")
                .then()
                .body("id", equalTo((int) petId))
                .body("name", equalTo("Tommy"));
    }

    @Test
    @Order(2)
    void getPet() {

        given()
                .when()
                .get("/pet/" + petId)
                .then()
                .body("id", equalTo((int) petId))
                .body("name", equalTo("Tommy"))
                .body(matchesJsonSchemaInClasspath("schema/PetSchema.json"));
    }

    @Test
    @Order(3)
    void updatePet() {

        given()
                .body(PetPayload.updatePet(petId))
                .when()
                .put("/pet")
                .then()
                .body("status", equalTo("sold"));
    }

    @Test
    @Order(4)
    void deletePet() {

        given()
                .header("api_key", "special-key")
                .when()
                .delete("/pet/" + petId)
                .then();
    }
}