package utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.InputStream;
import java.util.Properties;

import static utils.EnvReader.dotenv;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            InputStream input =
                    ConfigReader.class
                            .getClassLoader()
                            .getResourceAsStream("config.properties");

            properties.load(input);


        } catch (Exception e){

            throw new RuntimeException(
                    "Unable to load config.properties"
            );
        }
    }


    public static String get(String key){

        String value = dotenv.get(key);

        if(value == null){
            value = properties.getProperty(key);
        }

        return value;
    }
}