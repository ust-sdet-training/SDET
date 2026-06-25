package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;
import utils.Config;


import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class PetstoreTest {

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
                "id":333333,
                "category":{
                    "id":33,
                    "name":"doggos"
                    },
                "name":"dell36",
                "status":"available"
                }
                """;

        given()
                .spec(RequestSpecs.request_spec())
                .body(body)
                .when()
                .post("/pet")
                .then()
                .spec(ResponseSpecs.okJson_200())
                .body("id", equalTo(333333))
                .body("name", equalTo("dell36"))
                .body("status", equalTo("available"))
                .body(matchesJsonSchemaInClasspath("schemas/create-pet.json"));
    }

    @Test
    @DisplayName("Find pet by ID")
    void get_by_id(){
        String petId = "333333";
        given()
                .spec(RequestSpecs.request_spec())
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .spec(ResponseSpecs.okJson_200())
                .body("id", equalTo(333333))
                .body("name", equalTo("dell36"))
                .body("status", equalTo("available"))
                .body(matchesJsonSchemaInClasspath("schemas/get-by-id.json"));
    }

    @Test
    @DisplayName("Update an existing pet")
    void update_pet_by_id(){
        String body = """
                {
                "id":333333,
                "category":{
                    "id":33,
                    "name":"doggosUpdated"
                    },
                "name":"dell36Updated",
                "status":"pending"
                }
                """;
        given()
                .spec(RequestSpecs.request_spec())
                .body(body)
                .when()
                .put("/pet")
                .then()
                .spec(ResponseSpecs.okJson_200())
                .body("name", equalTo("dell36Updated"))
                .body("status", equalTo("pending"))
                .body("category.name", equalTo("doggosUpdated"));
    }

    @Test
    @DisplayName("Find pets by status")
    void find_pets_by_status(){
        given()
                .spec(RequestSpecs.request_spec())
                .queryParam("status", "sold")
                .when()
                .get("/pet/findByStatus")
                .then()
                .spec(ResponseSpecs.okJson_200());
    }

    @Test
    @DisplayName("Delete a pet")
    void delete_pet_by_id(){
        given()
                .spec(RequestSpecs.request_spec())
                .header("api_key", Config.API_KEY)
                .pathParam("petId", "455674")
                .then()
                .spec(ResponseSpecs.Error_404());
    }





}
