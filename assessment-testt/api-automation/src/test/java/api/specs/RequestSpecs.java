package api.specs;

import api.config.AppConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecs {
    private RequestSpecs() {
        // helper class
    }

    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
            .setBaseUri(AppConfig.BASE_URL)
            .setContentType(ContentType.JSON)
            .setRelaxedHTTPSValidation()
            .build();
    }

    public static RequestSpecification authSpec(String token) {
        return defaultSpec()
            .auth().oauth2(token);
    }
}
