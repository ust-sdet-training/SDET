package com.apitesting.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class TestData {

    private TestData() {
    }

    public static final String FROM = "PUN";
    public static final String TO = "DEL";
    public static final String CABIN = "economy";
    public static final int PASSENGERS = 1;

    public static final String DATE =
            LocalDate.now()
                    .plusDays(23)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE);


}