package spec;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;


public class RequestSpec {


    public static RequestSpecification request(){

        return new RequestSpecBuilder()

                .setBaseUri(
                        ConfigReader.get("base.url")
                )

                .setContentType(
                        "application/json"
                )

                .build();

    }

}