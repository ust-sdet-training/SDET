package utils;

import io.github.cdimascio.dotenv.Dotenv;


public class EnvReader {

    static final Dotenv dotenv = Dotenv.load();

    public static String get(String key){
        return dotenv.get(key);
    }
}