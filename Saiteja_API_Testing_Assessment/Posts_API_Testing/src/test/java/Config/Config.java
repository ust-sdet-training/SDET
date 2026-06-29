package Config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class Config {

    @BeforeEach
    void setup(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
}
