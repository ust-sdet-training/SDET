package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import models.PostRequest;

public class JsonPlaceholderTests extends BaseTest {


    @Test
    public void createPostTest() {

        PostRequest request = new PostRequest();
        request.title = "My Post";
        request.body = "Testing API";
        request.userId = 1;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(request)
                .when()
                .post("/posts");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    public void updatePostTest() {

        String body = """
            {
              "id": 1,
              "title": "Updated Title",
              "body": "Updated Body",
              "userId": 1
            }
            """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put("/posts/1");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test
    public void deletePostTest() {

        Response response = given()
                .when()
                .delete("/posts/1");

        Assert.assertEquals(response.getStatusCode(), 200);
    }



    @Test
    public void getPostsTest() {

        Response response = given()
                .when()
                .get("/posts");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void queryParamTest() {

        Response response = given()
                .queryParam("userId", 1)
                .when()
                .get("/posts");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void headersTest() {

        Response response = given()
                .when()
                .get("/posts");

        String contentType = response.getHeader("Content-Type");

        System.out.println(contentType);

        Assert.assertTrue(contentType.contains("application/json"));
    }

    @Test
    public void bodyValidationTest() {

        Response response = given()
                .when()
                .get("/posts/1");

        String title = response.jsonPath().getString("title");

        System.out.println(title);

        Assert.assertNotNull(title);
    }


    @Test
    public void dataDrivenTest() {

        int[] ids = {1, 2, 3};

        for (int id : ids) {

            Response response = given()
                    .when()
                    .get("/posts/" + id);

            Assert.assertEquals(response.getStatusCode(), 200);
        }
    }


    @Test
    public void invalidEndpointTest() {

        Response response = given()
                .when()
                .get("/wrongendpoint");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 404);
    }
    @Test
    public void invalidPayloadTest() {

        String invalidBody = "{ \"email\": \"\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(invalidBody)
                .when()
                .post("/posts");

        response.prettyPrint();

        Assert.assertTrue(
                response.getStatusCode() == 400 ||
                        response.getStatusCode() == 201
        );
    }
}