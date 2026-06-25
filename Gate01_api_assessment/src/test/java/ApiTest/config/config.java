package ApiTest.config;

import io.github.cdimascio.dotenv.Dotenv;

public class config {
    public static String Base_URI="https://restful-booker.herokuapp.com";
    public static String Base_path="/booking";
    private static Dotenv dotenv=Dotenv.load();
    public static String token=dotenv.get("token");
}
