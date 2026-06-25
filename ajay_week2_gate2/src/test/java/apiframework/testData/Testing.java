package apiframework.testData;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static apiframework.spec.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Testing {

    @Test
    @DisplayName("FOR GET FLOW")
    void API_FLOW()
    {



        given()
                .spec(getReq())
            .when()
                .get("/booking")
            .then()
                .spec(getRes())
                .body( "bookingid", notNullValue());


//        given()
//                .spec(getReq())
//                .body(POST_ORDER)
//                .when()
//                .post("/booking")
//                .then()
//                .spec(getRes());
//        //.body( "bookingid", notNullValue());



//        given()
//                .spec(putReq())
//                .body(POST_ORDER)
//                .when()
//                .put("/booking/1")
//                .then()
//                .log().all()
//                .spec(getRes());
//                //.body( "bookingid", notNullValue());



//        given()
//                .spec(delReq())
//                .when()
//                .get("/booking/1")
//                .then()
//                .spec(getRes());



    }


    static Map<String,String> token= Map.of("password","password123","username","admin");


    @Test
    @DisplayName("Post api auth test in restful-booker")
    void posttest()
    {
        given()
                .when()
                .spec(postReq())
                .body(token)
                .post("/auth")
                .then()
                .spec(getRes())
                .body("token",notNullValue());


    }



}


