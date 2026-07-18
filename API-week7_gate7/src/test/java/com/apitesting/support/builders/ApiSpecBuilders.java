    package com.apitesting.support.builders;

    import com.apitesting.config.Config;
    import io.restassured.builder.RequestSpecBuilder;
    import io.restassured.builder.ResponseSpecBuilder;
    import io.restassured.http.ContentType;
    import io.restassured.specification.RequestSpecification;
    import io.restassured.specification.ResponseSpecification;

    public class ApiSpecBuilders {

        public static RequestSpecification reqSpecpost() {
            return new RequestSpecBuilder()
                    .setBaseUri(Config.baseurl)
                    .setBasePath("/api")
                    .setContentType(ContentType.JSON)
                    .build();
        }

        public static ResponseSpecification resSpecpost() {
            return new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .build();
        }

        public static RequestSpecification reqSpecget(String token) {
            return new RequestSpecBuilder()
                    .setBaseUri(Config.baseurl)
                    .setBasePath("/api")
                    .setContentType(ContentType.JSON)
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        }

        public static ResponseSpecification resSpecget() {
            return new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .build();
        }

        public static RequestSpecification notokenreqSpecget() {
            return new RequestSpecBuilder()
                    .setBaseUri(Config.baseurl)
                    .setBasePath("/api")
                    .setContentType(ContentType.JSON)
                    .build();
        }

        public static RequestSpecification invalidokenreqSpecget() {
            return new RequestSpecBuilder()
                    .setBaseUri(Config.baseurl)
                    .setBasePath("/api")
                    .setContentType(ContentType.JSON)
                    .addHeader("Authorization", "Bearer Invalid" )
                    .build();
        }
    }
