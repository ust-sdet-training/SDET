package ust.sdet.support;

public class Config {
    private Config(){}

    public static String baseUrl(){
        return TestEnvironment.required("baseUrl").replaceAll("/$","");
    }

    public static String Catalog(){
        return baseUrl() + "/catalog";
    }

    public static String Home(){
        return baseUrl() + "/";
    }


    public static boolean headless(){
        return Boolean.parseBoolean(TestEnvironment.required("headless"));
    }

}
