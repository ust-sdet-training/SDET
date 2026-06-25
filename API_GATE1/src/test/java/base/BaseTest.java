package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import support.TestEnvironment;

public class BaseTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
//        RestAssured.basePath= "/booking";

    }
}