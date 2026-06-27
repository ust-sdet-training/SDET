package ust.gama.sdet.Gate4.selenide.helper;

public class Config {

    private Config(){
    }
//    public static String baseUrl(){
    ////        return EnvCheck.required("Selenium_URL").replaceAll("/$", "");
//        String url =EnvCheck.required("SELENIUM_URL");
//        return (url != null && url.endsWith("/"))
//                        ? url.substring(0, url.length() - 1)
//                        : url;
//
//    }
    public static String baseUrl(){
        return System.getProperty("baseUrl", "http://localhost:5173").replaceAll("/$", "");
    }

    public static String catalogURL(){
        return baseUrl() + "/catalog";
    }
    public static String homeURL(){
        return baseUrl() + "/home";
    }

    public static boolean headless(){
        return Boolean.parseBoolean(System.getProperty("headless", "true"));
    }

    public static boolean headed(){
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}

