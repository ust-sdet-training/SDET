package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import specs.RequestSpec;
import specs.ResponseSpec;

public class GetPostValidationTest extends BaseTest {

    @Test
    public void validateGetPost() {

        given()
                .spec(RequestSpec.requestSpecification())

                .when()
                .get("/posts/1")

                .then()
                .spec(ResponseSpec.responseSpecification())


                .body("id", equalTo(1))


                .body("userId", notNullValue())


                .body("title", not(emptyOrNullString()))


                .body("body", not(emptyOrNullString()));
    }
}