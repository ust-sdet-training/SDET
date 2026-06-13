package com.ust.sdet.api.apiframework.config;

import io.github.cdimascio.dotenv.Dotenv;

    public class ConfigManager {

        private static final Dotenv dotenv = Dotenv.load();

        public static final String BASE_URL =
                dotenv.get("BASE_URL", "http://localhost:4000");

        public static final String CLIENT_ID =
                dotenv.get("OAUTH_CLIENT_ID", "retail-ops-client");

        public static final String CLIENT_SECRET =
                dotenv.get("OAUTH_CLIENT_SECRET");

        public static final String VIEWER_CLIENT_ID =
                dotenv.get("OAUTH_VIEWER_CLIENT_ID", "retail-viewer-client");

        public static final String VIEWER_CLIENT_SECRET =
                dotenv.get("OAUTH_VIEWER_CLIENT_SECRET");

        public static final String EXPIRED_CLIENT_ID =
                dotenv.get("OAUTH_EXPIRED_CLIENT_ID", "retail-expired-client");

        public static final String EXPIRED_CLIENT_SECRET =
                dotenv.get("OAUTH_EXPIRED_CLIENT_SECRET");

    }


