package api.tests;

import api.api.ApiClient;
import api.config.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserApiTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldVerifyLoginUserRole() throws Exception {
        JsonPath authResponse = ApiClient.login(AppConfig.USER_EMAIL, AppConfig.USER_PASSWORD);
        String role = authResponse.getString("role");
        assertThat(role, equalTo("traveller"));
    }
}
