package support;

import static io.restassured.RestAssured.given;

import services.AuthService;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if(token == null) {
            token = new AuthService().generateToken();
        }

        return token;
    }
}
