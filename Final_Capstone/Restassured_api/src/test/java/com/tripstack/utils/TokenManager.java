package com.tripstack.utils;


import com.tripstack.clients.AuthClient;


public class TokenManager {


    private static String token;



    public static String getToken(){


        if(token==null){

            token=new AuthClient().login();

        }


        return token;

    }


}