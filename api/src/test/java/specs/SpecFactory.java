package specs;


import config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;



public final class SpecFactory {


    private SpecFactory() {
        // Prevent object creation
    }



    // =========================
    // Common Request Specification
    // =========================


    public static RequestSpecification getRequestSpec(){


        return new RequestSpecBuilder()

                .setBaseUri(
                        ConfigManager.baseUrl()
                )

                .setContentType(JSON)

                .setAccept(JSON)

                .build();

    }



    // =========================
    // Authenticated Request Specification
    // =========================


    public static RequestSpecification getAuthRequestSpec(
            String token
    ){


        return new RequestSpecBuilder()

                .setBaseUri(
                        ConfigManager.baseUrl()
                )

                .setContentType(JSON)

                .setAccept(JSON)

                .addHeader(
                        "Authorization",
                        "Bearer " + token
                )

                .build();

    }



    // =========================
    // Response Specification
    // =========================


    public static ResponseSpecification getResponseSpec(){


        return new ResponseSpecBuilder()

                .expectContentType(JSON)

                .expectResponseTime(
                        lessThan(
                                ConfigManager.timeout()
                        )
                )

                .build();

    }

}