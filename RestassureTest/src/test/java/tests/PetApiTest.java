package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import javafx.application.Application;
import model.PetResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.SpecFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class PetApiTest extends BaseTest {


    @Test
    @DisplayName("Finding Pet")
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