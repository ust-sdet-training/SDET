package com.Capstone.api.support.SpecFactory;

import com.Capstone.api.config.Config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class utils {
        public static String travelDate() {
            return LocalDate.now().plusDays(Config.DAYS_AHEAD).format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
