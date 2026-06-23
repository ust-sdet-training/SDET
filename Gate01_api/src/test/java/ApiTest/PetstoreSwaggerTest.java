package ApiTest;
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
    @Test
    void shouldLoadPetstoreSwaggerJson() {
        given()
                .when()
                .get("/swagger.json")
                .then()
                .statusCode(200)
                .body("swagger", equalTo("2.0"));
    }
    @Test
    void shouldReturnStockInventory() {
        given()
                .when()
                .get("/store/inventory")
                .then()
                .statusCode(200)
                .body("", hasKey("sold"));
    }
    @Test
    void getPetById() {
        given()
                .when()
                .get("/pet/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", notNullValue());
    }
    @Test
    void createPet() {
        String payload = """
    {
      "id": 99999,
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
                .statusCode(200)
                .body("name", equalTo("Tommy"))
                .body("status", equalTo("available"));
    }
    @Test
    void updatePet() {
        String payload = """
    {
      "id": 99999,
      "name": "TommyUpdated",
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
                .body("name", equalTo("TommyUpdated"))
                .body("status", equalTo("sold"));
    }
    @Test
    void deletePet() {
        given()
                .when()
                .delete("/pet/99999")
                .then()
                .statusCode(200);
    }

}
