//package java;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetStoreTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";
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
//    @Test
//    void getPetById() {
//        given()
//                .when()
//                .get("/pet/7471253926809500000")
//                .then()
//                .statusCode(200)
//                .body("id", equalTo("7471253926809500000"))
//                .body("name", notNullValue());
//    }
    @Test
    void createPet() {
        String payload = """
    {
      "id": 29345,
      "name": "Dobermon",
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
                .body("name", equalTo("Dobermon"))
                .body("status", equalTo("available"));
    }
    @Test
    void updatePet() {
        String payload = """
    {
      "id": 29345,
      "name": "DobermonUpdated",
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
                .body("name", equalTo("DobermonUpdated"))
                .body("status", equalTo("sold"));
    }
    @Test
    void deletePet() {
        given()
                .when()
                .delete("/pet/29345")
                .then()
                .statusCode(200);
    }

}