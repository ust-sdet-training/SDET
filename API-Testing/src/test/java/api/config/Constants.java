package api.config;

import api.config.TestEnvironment;

public class Constants {


    public static final String baseUrl = TestEnvironment.required("BASE_URL");
    public static final String API_KEY =TestEnvironment.required("SUPER_KEY");


}
