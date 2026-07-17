package com.apitesting.support;

import com.apitesting.config.ApiConfig;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {
    private DateUtils() {
    }

    public static String travelDate() {
        return LocalDate.now().plusDays(ApiConfig.DAYS_AHEAD).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
