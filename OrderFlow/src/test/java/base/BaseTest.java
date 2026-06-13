package base;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import specs.RequestSpecs;

public class BaseTest {

    protected static RequestSpecification requestSpec;

    @BeforeAll
    static void setup() {

        requestSpec = RequestSpecs.requestSpec();
    }
}