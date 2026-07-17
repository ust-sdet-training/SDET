package utils;


import java.util.UUID;


public final class RandomUtils {


    private RandomUtils(){
    }



    public static String randomUUID(){


        return UUID
                .randomUUID()
                .toString();

    }


}