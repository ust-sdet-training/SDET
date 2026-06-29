package spec;

import config.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class SpecFactory {
    public RequestSpecification createRequestSpec() {
        ConfigManager configManager = new ConfigManager();
        return given()
                .baseUri(configManager.getBaseUrl())
                .contentType(ContentType.JSON);
    }
}
