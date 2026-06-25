package utils;

import utils.BaseTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public class SpecFactory extends BaseTest {


    public static String petURL() {
        return "/pet";
    }

    public static String petByIdURL() {
        return "/pet/{petId}";
    }

    public static String petByStatusURL() {
        return "/pet/findByStatus";
    }

    public static String orderURL() {
        return "/store/order";
    }

    public static String orderByIdURL() {
        return "/store/order/{orderId}";
    }

    public static String inventoryURL() {
        return "/store/inventory";
    }

    public static String userURL() {
        return "/user";
    }

    public static String userByNameURL() {
        return "/user/{username}";
    }

    public static String loginURL() {
        return "/user/login";
    }

    public static String logoutURL() {
        return "/user/logout";
    }


    public static RequestSpecification reqSpec() {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }


    public static ResponseSpecification successResponseSpec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(5000L))
                .build();
    }

    public static ResponseSpecification deleteResponseSpec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}