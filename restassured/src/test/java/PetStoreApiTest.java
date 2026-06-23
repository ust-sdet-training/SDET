import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetStoreApiTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";
    }

    @Test
    void returns_stock_inventory() {
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
                .get("/pet/2236")
                .then()
                .statusCode(200)
                .body("id", equalTo(2236))
                .body("name", notNullValue());
    }
    @Test
    void createPet() {
        String payload = """
    {
      "id": 3333,
      "name": "Harry",
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
                .body("name", equalTo("Harry"))
                .body("status", equalTo("available"));
    }
    @Test
    void updatePet() {
        String payload = """
    {
      "id": 3333,
      "name": "HarryUpd",
      "status": "pending"
    }
    """;
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("HarryUpd"))
                .body("status", equalTo("pending"));
    }
    @Test
    void deletePet() {
        given()
                .when()
                .delete("/pet/3333")
                .then()
                .statusCode(200);
    }

}