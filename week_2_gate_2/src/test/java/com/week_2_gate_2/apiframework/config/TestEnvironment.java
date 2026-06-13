package com.week_2_gate_2.apiframework.config;

public class TestEnvironment {
public static String value(String Value , String Fallback){
        String keyBoardValue = System.getProperty(Value);
        if(keyBoardValue !=null && !keyBoardValue.isBlank()){
            return  keyBoardValue;
        }
        String envValue = System.getenv(Value);
        return envValue == null || envValue.isBlank() ? Fallback : envValue;
    }
    
} 