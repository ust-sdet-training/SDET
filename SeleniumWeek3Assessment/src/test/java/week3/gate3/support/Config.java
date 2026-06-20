package week3.gate3.support;

public class Config {
    private Config(){

    }

    public static String baseUrl(){
        return TestEnvironment.optional("BASE_URL","");
    }

    public static String catalogUrl(){
        return baseUrl() + "/catalog";
    }

    public static boolean headless(){
        return Boolean.parseBoolean(TestEnvironment.optional("HEADLESS",""));
    }

}
