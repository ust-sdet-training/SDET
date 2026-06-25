package com.ust.sdet.test;

import com.ust.sdet.baseTest.BaseTest;
import com.ust.sdet.dataModel.PetObj;
import com.ust.sdet.spec.AllSpec;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PetTest  extends BaseTest {
    private static final long PET_ID = 5000;

    @Test
    void createPet() {

        PetObj pet = new PetObj(
                PET_ID,
                "Dog",
                "available"
        );

        given()
                .contentType("application/json")
                .body(pet)

                .when()
                .post("/pet")

                .then()
                .spec(AllSpec.successResponse())
                .body("id", equalTo(5000))
                .body("name", equalTo("Dog"))
                .body("status", equalTo("available"));
    }
    @Test
    void getPet() {

        given()
                .pathParam("petId", 2)

                .when()
                .get("/pet/{petId}")

                .then()
                .spec(AllSpec.successResponse())
                .body("id", equalTo(2))
                .body("name", notNullValue());
    }
    @Test
    void invalidPet() {

        given()
                .pathParam("petId", 999999)

                .when()
                .get("/pet/{petId}")

                .then()
                .spec(AllSpec.notFoundResponse());
    }

}
