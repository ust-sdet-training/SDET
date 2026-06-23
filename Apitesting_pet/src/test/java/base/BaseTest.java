package base;

import io.restassured.specification.RequestSpecification;
import specs.RequestSpecs;

public class BaseTest {

    protected RequestSpecification requestSpec =
            RequestSpecs.getRequestSpecification();
}