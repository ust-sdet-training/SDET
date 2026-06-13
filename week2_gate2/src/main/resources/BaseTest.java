package base;

import config.ConfigReader;
import config.DatabaseConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import support.DbSupport;

public class BaseTest {

    protected static DbSupport dbSupport;

    @BeforeAll
    public static void setUp() throws Exception{

        RestAssured.baseURI =
                ConfigReader.get("base.url");

        dbSupport = new DbSupport(
                DatabaseConfig.fromEnvironment()
        );

        dbSupport.createOrdersTableIfNotExists();
    }
}