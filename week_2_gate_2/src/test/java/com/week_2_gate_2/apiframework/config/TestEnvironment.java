package com.week_2_gate_2.apiframework.config;

import io.github.cdimascio.dotenv.Dotenv;

public class TestEnvironment {
    private static final Dotenv dotenv = Dotenv.load();
public static String value(String Value , String Fallback){

        String keyBoardValue = dotenv.get(Value);
        if(keyBoardValue !=null && !keyBoardValue.isBlank()){
            return  keyBoardValue;
        }
        String envValue = System.getenv(Value);
        return envValue == null || envValue.isBlank() ? Fallback : envValue;
    }
    
} 