package tests;
import base.BaseTest;
import model.PetResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.SpecFactory;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;



public class PetApiTest extends BaseTest {
    @Test
    @DisplayName("Performing crud operations in the Pet Api")
    void petCrudFlow() {
        Map<String, Object> petBody = Map.of(
                "id", 101,
                "name", "july",
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
        assertEquals("july", createdPet.name());
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
        assertEquals("july", fetchedPet.name());
        Map<String, Object> updateBody = Map.of(
                "id", petId,
                "name", "july Updated",
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
        assertEquals("july Updated", updatedPet.name());
        assertEquals("sold", updatedPet.status());
        given()
                .pathParam("petId", petId)
                .when()
                .delete(SpecFactory.petByIdURL())
                .then()
                .spec(SpecFactory.successResponseSpec());
    }
    @Test
    @DisplayName("Finding Pet by the status")
    void findPetByStatus() {

        PetResponse[] pets =
                given()
                        .spec(SpecFactory.reqSpec())
                        .queryParam("status", "available")
                        .when()
                        .get(SpecFactory.petByStatusURL())
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(PetResponse[].class);
        assertTrue(pets.length > 0);
    }
}