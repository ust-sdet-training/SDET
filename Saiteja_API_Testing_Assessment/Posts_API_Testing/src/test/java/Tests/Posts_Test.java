package Tests;

import Config.Config;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class Posts_Test extends Config {

    @Test
    @DisplayName("Get all posts")
    void getAllPosts(){
        Response response = given()
                .get("/users");

        assertEquals(200,response.getStatusCode());


    }

    @Test
    @DisplayName("Get all posts of one user")
    void getUserPosts(){
        Response response = given()
                .queryParam("userId",1)
                .get("/posts");

        assertEquals(200,response.getStatusCode());

        List<Integer> userIdList = response.jsonPath().getList("userId");

        assertFalse(userIdList.isEmpty(),"UserId is empty");

        for(Integer userId : userIdList){
            assertEquals(1,userId,"UserId is not same in the Response");
        }

    }

}
