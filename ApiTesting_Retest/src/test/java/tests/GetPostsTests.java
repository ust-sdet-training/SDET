package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

public class GetPostsTests extends BaseTest {

    @Test
    @DisplayName("Get Posts")
    void GetPosts(){
        given()
                .spec(requestSpec)
                .when()
                .get("/posts")
                .then()
                .spec(ResponseSpecs.success200())
                .body("[0].userId", equalTo(1))
                .body("[0].id", equalTo(1))
                .body("[0].title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
                .body("[0].body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"))
                .body(matchesJsonSchemaInClasspath("schemas/posts.schema.json"));
    }
    @Test
    @DisplayName("Get Posts by Id")
    public void getPostsById() {

        given()
                .spec(requestSpec)


                .when()
                .get("/comments?postId=1")
                .then()
                .spec(ResponseSpecs.success200())
                .body("[0].postId", equalTo(1))
                .body("[0].id", equalTo(1))
                .body("[0].email", equalTo("Eliseo@gardner.biz"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/getbypostid.schema.json"));
    }


}
