package com.week03_gate3_muhammedali.support;

import javax.print.DocFlavor.STRING;

public class Config {
    private Config() {}
    public static String baseUrl() {
        return TestEnvironment.required("baseUrl").replaceAll("/$", "");
    }
 
    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(TestEnvironment.optional("HEADLESS", "false").toLowerCase());
    }

    public static String DriverName(){
        return TestEnvironment.required("DRIVER").toLowerCase();
    }
}
 