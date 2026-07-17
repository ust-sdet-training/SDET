package com.tripstack.db;


import java.sql.*;


import com.tripstack.config.Config;



public class DBUtils {



    public static Connection connection()
            throws Exception{


        return DriverManager.getConnection(

                Config.get("db.url"),

                Config.get("db.user"),

                Config.get("db.password")

        );


    }



}