package org.sdet.base;

import io.restassured.specification.RequestSpecification;
import org.sdet.specs.RequestSpecs;

public class BaseAPI {

    protected RequestSpecification requestSpec;

    public BaseAPI() {
        requestSpec = RequestSpecs.requestSpec();
    }

}