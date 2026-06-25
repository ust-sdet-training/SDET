package ApiTest.test;

import ApiTest.config.config;
import ApiTest.test.specFactory.reqSpec;
import ApiTest.test.specFactory.respSpec;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class herokuappTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = config.Base_URI;
        RestAssured.basePath = config.Base_path;
    }

    private String getToken() {

        return given()
                .contentType("application/json")
                .body("""
                        {
                          "username":"admin",
                          "password":"password123"
                        }
                        """)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    @Test
    public void getBooking() {

        given()
                .spec(reqSpec.getRequestSpecification())
                .when()
                .get("/1")
                .then()
                .spec(respSpec.okSuccess());
    }

    @Test
    public void createBooking() {

        String payload = """
                {
                    "firstname":"Jim",
                    "lastname":"Brown",
                    "totalprice":111,
                    "depositpaid":true,
                    "bookingdates":{
                        "checkin":"2018-01-01",
                        "checkout":"2019-01-01"
                    },
                    "additionalneeds":"Breakfast"
                }
                """;

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post()
                .then()
                .spec(respSpec.okSuccess());
    }


}