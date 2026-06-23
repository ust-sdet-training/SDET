package gama.qa.gate3.secure;

import io.github.cdimascio.dotenv.Dotenv;

public class credentials {
    private static Dotenv dotenv = Dotenv.load();

    public static final String API_BASEURL = dotenv.get("API_baseurl");

}
