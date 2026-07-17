package api.tripstack.utils;

import java.time.LocalDate;
import java.time.ZoneId;

public final class DateUtils {
    private DateUtils() { }
    public static String indiaDateAfter(int days) {
        return LocalDate.now(ZoneId.of("Asia/Kolkata")).plusDays(days).toString();
    }
}
