package api.base;

import api.specs.RequestSpecs;
import io.restassured.specification.RequestSpecification;

public abstract class BaseTest {

    protected RequestSpecification api() {
        return RequestSpecs.defaultSpec();
    }
}
