package base;

import specs.RequestSpec;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
    protected RequestSpecification requestSpec= RequestSpec.getRequestSpecification();
}
