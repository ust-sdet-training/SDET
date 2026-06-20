package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    static Properties properties;

    public static void loadConfig(){

        properties = new Properties();
         try{
             properties.load(new FileInputStream("src/test/resources/config.properties"));
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
    }

    public static String getConfigValue(String key) {
        return properties.getProperty(key);
    }
}
