package tests;

import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetStoreTest {

    @Test
    void getPetsByStatus() {
        given()
                .spec(SpecFactory.reqSpec())
                .queryParam("status", "available")

                .when()
                .get("/pet/findByStatus")

                .then()
                .spec(SpecFactory.ok200())
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue());
    }

    @Test
    void getPetById() {
        given()
                .spec(SpecFactory.reqSpec())
                .pathParam("petId", 1)

                .when()
                .get("/pet/{petId}")

                .then()
                .spec(SpecFactory.ok200())
                .body("id", equalTo(1))
                .body("name", notNullValue())
                .body("status", notNullValue());
    }

    @Test
    void getPetByTags() {
        given()
                .spec(SpecFactory.reqSpec())
                .queryParam("tags", "dog")

                .when()
                .get("/pet/findByTags")

                .then()
                .spec(SpecFactory.ok200())
                .body("size()", greaterThanOrEqualTo(0));
    }


    @Test
    void createPet() {
        String petPayload = """
                {
                  "id": 999,
                  "category": {
                    "id": 1,
                    "name": "Dogs"
                  },
                  "name": "Doggie",
                  "photoUrls": ["url1", "url2"],
                  "tags": [
                    {
                      "id": 1,
                      "name": "tag1"
                    }
                  ],
                  "status": "available"
                }
                """;

        given()
                .spec(SpecFactory.reqSpec())
                .body(petPayload)

                .when()
                .post("/pet")

                .then()
                .spec(SpecFactory.ok200())
                .body("id", equalTo(999))
                .body("name", equalTo("Doggie"))
                .body("status", equalTo("available"));
    }




    @Test
    void updatePet() {
        String updatePayload = """
                {
                  "id": 999,
                  "category": {
                    "id": 1,
                    "name": "Dogs"
                  },
                  "name": "Updated Doggie",
                  "photoUrls": ["url1", "url2"],
                  "tags": [
                    {
                      "id": 1,
                      "name": "tag1"
                    }
                  ],
                  "status": "sold"
                }
                """;

        given()
                .spec(SpecFactory.reqSpec())
                .body(updatePayload)

                .when()
                .put("/pet")

                .then()
                .spec(SpecFactory.ok200())
                .body("id", equalTo(999))
                .body("name", equalTo("Updated Doggie"))
                .body("status", equalTo("sold"));
    }


    
    @Test
    void deletePet() {
        given()
                .spec(SpecFactory.reqSpec())
                .pathParam("petId", 999)

                .when()
                .delete("/pet/{petId}")

                .then()
                .spec(SpecFactory.ok200());
    }

    @Test
    void getOrderById() {
        given()
                .spec(SpecFactory.reqSpec())
                .pathParam("orderId", 1)

                .when()
                .get("/store/order/{orderId}")

                .then()
                .spec(SpecFactory.ok200())
                .body("id", notNullValue())
                .body("petId", notNullValue());
    }

    @Test
    void placeOrder() {
        String orderPayload = """
                {
                  "id": 99999,
                  "petId": 1,
                  "quantity": 2,
                  "shipDate": "2026-06-23T10:00:00Z",
                  "status": "placed",
                  "complete": false
                }
                """;

        given()
                .spec(SpecFactory.reqSpec())
                .body(orderPayload)

                .when()
                .post("/store/order")

                .then()
                .spec(SpecFactory.ok200())
                .body("id", notNullValue())
                .body("petId", equalTo(1))
                .body("quantity", equalTo(2));
    }

    @Test
    void deleteOrder() {
        given()
                .spec(SpecFactory.reqSpec())
                .pathParam("orderId", 99999)

                .when()
                .delete("/store/order/{orderId}")

                .then()
                .spec(SpecFactory.ok200());
    }
}

