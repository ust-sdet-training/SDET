package com.week_2_gate_2.dbframework.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.week_2_gate_2.dbframework.support.DbSupport;
import com.week_2_gate_2.dbframework.config.DBconfig;

public class DatabaseTests {

    
    private static DbSupport database;

     @BeforeAll
    static void setup(){
        database = new DbSupport(DBconfig.fromEnvironment());
    }

    @Test
    @DisplayName("Local M1:MySQL is reachable through JDBC")
    void localDBConnected() throws Exception{
        assertTrue(database.isReachable());
        
    }

}
