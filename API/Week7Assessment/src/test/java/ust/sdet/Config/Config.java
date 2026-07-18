package ust.sdet.Config;

public class Config {

    public String getBaseUrl(){
        return TestEnvironment.required("ARAVIND_BASE_URL_API");
    }

}
