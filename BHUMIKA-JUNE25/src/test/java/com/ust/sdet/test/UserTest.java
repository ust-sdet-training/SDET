package com.ust.sdet.test;

import com.ust.sdet.baseTest.BaseTest;
import com.ust.sdet.dataModel.UserObj;
import com.ust.sdet.spec.AllSpec;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest extends BaseTest {
        private static final String USERNAME = "bhumika";

        @Order(1)
        @Test
        void createUser() {

            UserObj user = new UserObj(
                    1,
                    USERNAME,
                    "Bhumika",
                    "Reddy",
                    "bhumika@gmail.com",
                    "test123",
                    "9876543210",
                    1
            );

            given(requestSpec)
                    .body(user)

                    .when()
                    .post("/user")

                    .then()
                    .statusCode(200);
        }
        @Order(2)
        @Test
        void getUser() {

            given(requestSpec)
                    .pathParam("username", USERNAME)

                    .when()
                    .get("/user/{username}")

                    .then()
                    .spec(AllSpec.successResponse())
                    .body("username", equalTo(USERNAME));
        }
        @Order(3)
        @Test
        void updateUser() {

            UserObj user = new UserObj(
                    1,
                    USERNAME,
                    "Bhumika",
                    "Updated",
                    "updated@gmail.com",
                    "test123",
                    "9999999999",
                    1
            );

            given(requestSpec)
                    .pathParam("username", USERNAME)
                    .body(user)

                    .when()
                    .put("/user/{username}")

                    .then()
                    .statusCode(200);
        }

        @Order(4)
        @Test
        void deleteUser() {
            given(requestSpec)
                    .pathParam("username", USERNAME)

                    .when()
                    .delete("/user/{username}")

                    .then()
                    .statusCode(200);
        }
        @Order(5)
        @Test
        void userNotFound() {

            given(requestSpec)
                    .pathParam("username", USERNAME)

                    .when()
                    .get("/user/{username}")

                    .then()
                    .spec(AllSpec.notFoundResponse());
        }
    }

