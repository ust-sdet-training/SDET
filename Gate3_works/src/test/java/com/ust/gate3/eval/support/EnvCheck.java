package com.ust.gate3.eval.support;

import io.github.cdimascio.dotenv.Dotenv;
import static org.junit.platform.commons.util.StringUtils.isBlank;

public class EnvCheck {

    private EnvCheck(){  }

    private static final Dotenv dotenv= Dotenv.load();

    public static String optional(String value, String backup_val){
        String isval= System.getProperty(value);
        if( isval!=null && !isval.isBlank()){
            return isval;
        }
        String envValue=dotenv.get(value);
        if( envValue!=null && !envValue.isBlank()){
            return envValue;
        }
        return backup_val;
    }

    public static String required(String value){
        String val=optional(value, "http://localhost:5173");
        if(val==null || val.isBlank()){
            throw new IllegalStateException("Missing required environment variable or system property: " + value);
        }
        return val;
    }


}