package base;

import db.DbSupport;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import specs.RequestSpecs;
import specs.ResponseSpecs;
import utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {

    protected static DbSupport dbSupport;

    @BeforeAll
    public static void setup() throws Exception {

        RestAssured.baseURI =
                ConfigReader.getBaseUrl();

        RestAssured
                .enableLoggingOfRequestAndResponseIfValidationFails();

        RequestSpecs.createRequestSpecification();

        ResponseSpecs.createResponseSpecifications();

        dbSupport =
                new DbSupport(
                        ConfigReader.getDbUrl(),
                        ConfigReader.getDbUsername(),
                        ConfigReader.getDbPassword()
                );

        assertTrue(
                dbSupport.isReachable(),
                "Database connection failed"
        );
    }
}