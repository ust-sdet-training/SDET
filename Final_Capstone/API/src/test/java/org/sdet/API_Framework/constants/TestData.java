package org.sdet.API_Framework.constants;

import org.sdet.API_Framework.config.ConfigManager;

public final class TestData {

    public static final String EMAIL = ConfigManager.getEmail();
    public static final String PASSWORD = ConfigManager.getPassword();

    public static final String FROM = "BOM";
    public static final String TO = "MAA";
    public static final int DATE_OFFSET = 9;

    public static final String TRAVEL_CLASS = "economy";

    public static final int PASSENGERS = 1;

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int CONFLICT = 409;
}