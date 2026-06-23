package tests;

import io.restassured.response.Response;
import model.Pet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import spec.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class PetstoreApiTests {

    @Test
    void getSinglePet() {

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/pet/1");

        response.then()
                .spec(SpecFactory.success200());

        assertNotNull(response.jsonPath().getString("name"));
    }

    @Test
    void createPet() {

        long id = System.currentTimeMillis() / 1000;
        Pet pet = new Pet(id, "Fluffy", "available");

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .body(pet)
                        .when()
                        .post("/pet");

        response.then()
                .spec(SpecFactory.success200());

        assertEquals(
                "Fluffy",
                response.jsonPath().getString("name"));
    }

    @Test
    void findPetByStatus() {

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .queryParam("status", "available")
                        .when()
                        .get("/pet/findByStatus");

        response.then()
                .spec(SpecFactory.success200());

        int size = response.jsonPath().getList("$").size();
        assertTrue(size >= 0);
    }

    @Test
    void validateHeaders() {

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/pet/1");

        response.then()
                .spec(SpecFactory.success200());

        assertTrue(
                response.getHeader("Content-Type")
                        .contains("application/json"));
    }

    @Test
    void validateResponseTime() {

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/store/inventory");

        assertTrue(response.time() < 5000);
    }

}

