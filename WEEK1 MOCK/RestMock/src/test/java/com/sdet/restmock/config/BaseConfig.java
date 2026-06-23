package com.sdet.restmock.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import static com.sdet.restmock.config.TestEnvironment.*;
public class BaseConfig {

    public static final String BASE_URL="http://petstore.swagger.io/v2";
    public static final String api_key=required("user_key");


}
