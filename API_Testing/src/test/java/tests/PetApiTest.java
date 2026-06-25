package tests;

import utils.BaseTest;
import models.PetResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.SpecFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class PetApiTest extends BaseTest {

    @Test
    @DisplayName("Perform CRUD operations")
    void petCrudFlow() {

        Map<String, Object> petBody = Map.of(
                "id", 1001,
                "name", "Tommy",
                "status", "available"
        );

        PetResponse createdPet =
                given()
                        .spec(SpecFactory.reqSpec())
                        .body(petBody)

                        .when()
                        .post(SpecFactory.petURL())

                        .then()
                        .spec(SpecFactory.successResponseSpec())
                        .extract()
                        .as(PetResponse.class);

        assertEquals("Tommy", createdPet.name());

        Long petId = createdPet.id();

        PetResponse fetchedPet =
                given()
                        .spec(SpecFactory.reqSpec())
                        .pathParam("petId", petId)

                        .when()
                        .get(SpecFactory.petByIdURL())

                        .then()
                        .spec(SpecFactory.successResponseSpec())
                        .extract()
                        .as(PetResponse.class);

        assertEquals(petId, fetchedPet.id());
        assertEquals("Tommy", fetchedPet.name());

        Map<String, Object> updateBody = Map.of(
                "id", petId,
                "name", "Tommy Updated",
                "status", "sold"
        );

        PetResponse updatedPet =
                given()
                        .spec(SpecFactory.reqSpec())
                        .body(updateBody)

                        .when()
                        .put(SpecFactory.petURL())

                        .then()
                        .spec(SpecFactory.successResponseSpec())
                        .extract()
                        .as(PetResponse.class);

        assertEquals("Tommy Updated", updatedPet.name());
        assertEquals("sold", updatedPet.status());

        given()
                .pathParam("petId", petId)

                .when()
                .delete(SpecFactory.petByIdURL())

                .then()
                .spec(SpecFactory.deleteResponseSpec());
    }

    @Test
    @DisplayName("Finding Pet by the status")
    void findPetByStatus() {

        PetResponse[] pets =
                given()
                        .spec(SpecFactory.reqSpec())
                        .queryParam("status", "sold")

                        .when()
                        .get(SpecFactory.petByStatusURL())

                        .then()
                        .spec(SpecFactory.successResponseSpec())
                        .extract()
                        .as(PetResponse[].class);

        assertTrue(pets.length > 0);
    }
}