package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import support.TokenManager;

public class RequestSpecs {

    public static RequestSpecification authOrders() {

        return new RequestSpecBuilder()

                .setBaseUri("http://localhost:4000")

                .setBasePath("/api/secure/orders")

                .addHeader(
                        "Authorization",
                        "Bearer " + TokenManager.getToken()
                )

                .setContentType(ContentType.JSON)

                .build();
    }
}
