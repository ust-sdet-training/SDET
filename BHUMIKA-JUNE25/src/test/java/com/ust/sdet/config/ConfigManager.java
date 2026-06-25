package com.ust.sdet.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigManager {

        private static final Dotenv dotenv = Dotenv.load();

        public static final String BASE_URL =
                dotenv.get("BASE_URL", "http://localhost:4000");


    }






