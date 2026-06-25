package APi;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetstoreSwaggerTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";
    }
    // Positive Test Cases
    @Test
    void shouldLoadSwaggerJson() {

        given()
                .when()
                .get("/swagger.json")
                .then()
                .log().all()
                .statusCode(200)
                .body("swagger", equalTo("2.0"));
    }
    @Test
    void createPet() {
        String payload = """
                {
                  "id": 999,
                  "name": "Tommy",
                  "status": "available"
                }
                """;
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("Tommy"))
                .body("status", equalTo("available"));
    }
    @Test
    void getPetById() {

        given()
                .when()
                .get("/pet/99")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(99));

    }
    @Test
    void updatePet() {

        String payload = """
                {
                  "id":999,
                  "name":"TommyUpdated",
                  "status":"sold"
                }
                """;

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .put("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("TommyUpdated"))
                .body("status", equalTo("sold"));
    }

    @Test
    void findPetByStatus() {
        given()
                .queryParam("status", "available")
                .when()
                .get("/pet/findByStatus")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", notNullValue());
    }

    @Test
    void deletePet() {
        given()
                .when()
                .delete("/pet/999")
                .then()
                .log().all()
                .statusCode(200);
    }
    // Negative Test Cases
    @Test
    void getInvalidPetId() {

        given()
                .when()
                .get("/pet/999998879")
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Pet not found"));
    }
    @Test
    void invalidStatusSearch() {
        given()
                .queryParam("status", "abcdef")
                .when()
                .get("/pet/findByStatus")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", empty());
    }
    @Test
    void createPetInvalidJson() {
        String payload = """
                {"id":,"name":"Dog"}
                """;
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/pet")
                .then()
                .log().all()
                .statusCode(400);
    }
}