package utils;

public class TokenManager {
    private static String token;
    public static void setToken(String value){
        token=value;
    }
    public static String getToken(){
        return token;
    }
}