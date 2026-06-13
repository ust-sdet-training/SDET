package ust.sdet.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers.*;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;


public class Assertions {

    public static ResponseSpecification assertOrderBody(){
        return new ResponseSpecBuilder()
                .expectBody("address",notNullValue())
                .expectBody("items.size()",greaterThan(0))
                .build();
    }

}
