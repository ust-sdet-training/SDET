package apiCrudTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class test {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";
    }
    @Test
    void createPet() {
        String payload = """
    {
      "id": 500,
      "name": "Chinnu",
      "status": "available"
    }
    """;
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Chinnu"))
                .body("status", equalTo("available"));
    }
    @Test
    void updatePet() {
        String payload = """
    {
      "id": 500,
      "name": "Bruno",
      "status": "available"
    }
    """;
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Bruno"))
                .body("status", equalTo("available"));
    }
    @Test
    void updatePetForSold() {
        String payload = """
    {
      "id": 500,
      "name": "Bruno",
      "status": "sold"
    }
    """;
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Bruno"))
                .body("status", equalTo("sold"));
    }

    @Test
    void deletePet() {
        given()
                .when()
                .delete("/pet/500")
                .then()
                .statusCode(200);
    }
    @Test
    void findPetByStatusSold() {

        given()
                .queryParam("status", "sold")

                .when()
                .get("/pet/findByStatus")

                .then()
                .statusCode(200);
    }

}
