package com.apitesting.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    private static final Dotenv DOTENV = Dotenv.configure().ignoreIfMissing().load();

    public static final String baseurl = DOTENV.get("BASE_URL");
    public static final String email = DOTENV.get("EMAIL");
    public static final String password = DOTENV.get("PASSWORD");
}