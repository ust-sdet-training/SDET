package API_FrameWork.Factory;

import API_FrameWork.config.ApiConfig;
import API_FrameWork.support.TestContext;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

    private RequestSpecFactory() {
    }

    /**
     * Returns a request specification.
     * If a JWT token exists in TestContext, it is automatically added.
     */
    public static RequestSpecification getRequestSpec() {

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URI)
                .setContentType(ContentType.JSON);

        if (TestContext.token != null && !TestContext.token.isBlank()) {
            builder.addHeader("Authorization", "Bearer " + TestContext.token);
        }

        return builder.build();
    }

    /**
     * Returns an authorized request specification using the supplied token.
     */
    public static RequestSpecification getAuthorizedRequestSpec(String token) {

        return new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URI)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}