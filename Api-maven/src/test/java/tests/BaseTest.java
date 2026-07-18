package tests;

import API_FrameWork.config.ApiConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    @BeforeAll
    static void configureBaseUri() {
        RestAssured.baseURI = ApiConfig.BASE_URI;
    }
}
