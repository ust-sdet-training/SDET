package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class SpecFactory {
    private static final String JSONPLACEHOLDER_BASE_URL = "https://jsonplaceholder.typicode.com";
   public static String resbody= "{\n" +
           "  \"id\": 1,\n" +
           "  \"title\": \"Updated Title\",\n" +
           "  \"body\": \"This is an updated post\",\n" +
           "  \"userId\": 1\n" +
           "}";
   public static String reqbody= "{\n" +
           "  \"title\": \"Test Post\",\n" +
           "  \"body\": \"This is a test post\",\n" +
           "  \"userId\": 1\n" +
           "}";
    public static RequestSpecification jsonPlaceholderSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(JSONPLACEHOLDER_BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();
    }
}
