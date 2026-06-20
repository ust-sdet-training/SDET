package support;

public class Config {
    private Config(){}

    public static String baseUrl(){
        return TestEnvironment.optional("baseUrl", "")
                .replaceAll("/$", "");
    }

    public static String catalogUrl(){
        return baseUrl() + "/catalog";
    }

    public static boolean headless(){
        return Boolean.parseBoolean(
                TestEnvironment.optional("headless", "")
        );
    }
}