package ust.sdet.Config;

public class Config {

    public String getBaseUrl(){
        return TestEnvironment.required("BASE_URL");
    }

}
