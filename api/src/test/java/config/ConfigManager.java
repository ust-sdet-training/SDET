package config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public final class ConfigManager {


    private static final Properties properties =
            new Properties();



    private ConfigManager(){}


    static {


        try(InputStream input =

                    ConfigManager.class
                            .getClassLoader()
                            .getResourceAsStream("config.properties")) {



            if(input == null){

                throw new RuntimeException(
                        "config.properties not found"
                );

            }


            properties.load(input);


        }
        catch(IOException exception){

            throw new RuntimeException(
                    "Failed to load configuration",
                    exception
            );

        }

    }



    public static String get(String key){

        String value =
                properties.getProperty(key);


        if(value == null){

            throw new RuntimeException(
                    "Missing configuration key : "
                            + key
            );

        }


        return value;

    }



    public static String baseUrl(){

        return get("base.url");

    }



    public static Environment environment(){


        return Environment.valueOf(
                get("environment")
                        .toUpperCase()
        );

    }



    public static long timeout(){

        return Long.parseLong(
                get("timeout")
        );

    }

}