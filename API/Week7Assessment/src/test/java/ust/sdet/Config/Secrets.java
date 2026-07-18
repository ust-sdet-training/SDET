package ust.sdet.Config;

public class Secrets {
    public String getPassword(){
        return TestEnvironment.required("ARAVIND_PASSWORD");

    }
}
