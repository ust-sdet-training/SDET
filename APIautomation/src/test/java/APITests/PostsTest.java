package APITests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class PostsTest {

    Response response;
    JsonPath jsonPath;

    @BeforeClass
    public void setup() {
        response =
                given()
                        .queryParam("userId", 1)
                        .when()
                        .get("https://jsonplaceholder.typicode.com/posts");
        response.prettyPrint();

        jsonPath = response.jsonPath();
    }

    @Test(priority = 1)
    public void sendGetRequest() {
        Assert.assertNotNull(response);
    }

    @Test(priority = 2)
    public void validateStatusCode() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void validateResponseNotEmpty() {
        List<Integer> userIds = jsonPath.getList("userId");
        Assert.assertFalse(userIds.isEmpty());
    }

    @Test(priority = 4)
    public void validateUserId() {
        List<Integer> userIds = jsonPath.getList("userId");

        for (Integer id : userIds) {
            Assert.assertEquals(id.intValue(), 1);
        }
    }
}