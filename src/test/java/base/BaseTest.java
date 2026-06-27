package base;

import factory.RequestSpecFactory;
import factory.ResponseSpecFactory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    static void setup() {

        RestAssured.requestSpecification =
                RequestSpecFactory.getRequestSpec();

        RestAssured.responseSpecification =
                ResponseSpecFactory.getResponseSpec();

        RestAssured.useRelaxedHTTPSValidation();
    }
}