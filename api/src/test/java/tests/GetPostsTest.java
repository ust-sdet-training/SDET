package tests;

import base.BaseTest;
import client.PostClient;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class GetPostsTest extends BaseTest {
    @Test
    public void getPostsByUserIdReturnsPostsForGivenUserId() {
        PostClient client = new PostClient(requestSpec);
        Response response = client.getPostsByUserId(1);

        response.then().statusCode(200);

        String responseBody = response.getBody().asString();
        Assert.assertFalse(responseBody.trim().isEmpty(), "Response body should not be empty");

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> posts = jsonPath.getList("$");

        Assert.assertFalse(posts.isEmpty(), "Response should not be empty");

        for (Map<String, Object> post : posts) {
            Assert.assertEquals(post.get("userId"), 1);
        }
    }
}
