package utils;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    public void setup() {

        RestAssured.baseURI = Config.BASE_URL;

    }

}
