package api.tests;

import api.api.ApiClient;
import api.config.AppConfig;
import api.models.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginApiTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldLoginSuccessfully() throws Exception {
        JsonPath response = ApiClient.login(AppConfig.USER_EMAIL, AppConfig.USER_PASSWORD);
        LoginResponse auth = mapper.readValue(response.prettyPrint(), LoginResponse.class);

        assertThat(auth.token, notNullValue());
        assertThat(auth.empId, equalTo("1024"));
        assertThat(auth.role, equalTo("traveller"));
    }
}
