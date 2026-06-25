package tests;

import config.Config;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import specs.SpecFactory;



import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class PetStoreTest {

    @BeforeAll
    static void setup(){
        RestAssured.baseURI= Config.BASE_URL;
        RestAssured.basePath=Config.BASE_PATH;
    }


    @Test
    @DisplayName("Create a new pet")
    void create_new_pet(){
        String body = """
                {
                "id":456382,
                "category":{
                    "id":35563,
                    "name":"doggie"
                    },
                "name":"superman",
                "status":"available"
                }
                """;

        given()
                .spec(SpecFactory.requestSpec())
                .body(body)
                .when()
                .post("/pet")
                .then()
                .spec(SpecFactory.success200())
                .body("id", equalTo(456382))
                .body("name", equalTo("superman"))
                .body("status", equalTo("available"));
//                .body(matchesJsonSchemaInClasspath("schemas/create-pet.json"));
    }

    @Test
    @DisplayName("Find pet by ID")
    void get_by_id(){
        String petId = "456382";
        given()
                .spec(SpecFactory.requestSpec())
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .spec(SpecFactory.success200())
                .body("id", equalTo(456382))
                .body("name", equalTo("superman"))
                .body("status", equalTo("available"));
//                .body(matchesJsonSchemaInClasspath("schemas/get-by-id.json"));
    }

    @Test
    @DisplayName("Update an existing pet")
    void update_pet_by_id(){
        String body = """
                {
                "id":456382,
                "category":{
                    "id":35563,
                    "name":"doggieUpdated"
                    },
                "name":"superman_update",
                "status":"pending"
                }
                """;
        given()
                .spec(SpecFactory.requestSpec())
                .body(body)
                .when()
                .put("/pet")
                .then()
                .spec(SpecFactory.success200())
                .body("name", equalTo("superman_update"))
                .body("status", equalTo("pending"))
                .body("category.name", equalTo("doggieUpdated"));
    }

    @Test
    @DisplayName("Find pets by status")
    void find_pets_by_status(){
        given()
                .spec(SpecFactory.requestSpec())
                .queryParam("status", "sold")
                .when()
                .get("/pet/findByStatus")
                .then()
                .spec(SpecFactory.success200());
    }

    @Test
    @DisplayName("Delete a pet")
    void delete_pet_by_id(){
        given()
                .spec(SpecFactory.requestSpec())
                .header("api_key", Config.API_KEY)
                .pathParam("petId", "455674")
                .then()
                .statusCode(404);
//                .spec(ResponseSpecs.Error_404());
    }





}
