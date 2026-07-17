package ust.sdet.Data;


import ust.sdet.Config.Secrets;

import java.util.Map;

public class TestDataBuilder {

    Secrets secrets = new Secrets();


    public Map<String, String> buildLoginPayload(String user) {
        return Map.of(
                "email", user.toLowerCase()+"@tripstack.test",
                "password", secrets.getPassword()
        );
    }
}
