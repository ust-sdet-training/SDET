package tests;

import config.ConfigReader;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class Authentication {
    @Test
    void checkConfig() {

        System.out.println(ConfigReader.BASE_URL);
        System.out.println(ConfigReader.CLIENT_ID);
        System.out.println(ConfigReader.CLIENT_SECRET);
    }




}
