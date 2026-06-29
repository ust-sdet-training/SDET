package base;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import spec.SpecFactory;

public class BaseTest {
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        SpecFactory specFactory = new SpecFactory();
        requestSpec = specFactory.createRequestSpec();
    }
}
