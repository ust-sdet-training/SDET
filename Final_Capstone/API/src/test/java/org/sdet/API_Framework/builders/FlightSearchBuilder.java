package org.sdet.API_Framework.builders;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public final class FlightSearchBuilder {

    private FlightSearchBuilder() {
    }

    public static Map<String, Object> validSearch() {

        Map<String, Object> search = new HashMap<>();

        search.put("from", "BOM");
        search.put("to", "MAA");
        search.put("date", LocalDate.now().plusDays(9).toString());
        search.put("class", "economy");
        search.put("pax", 1);

        return search;
    }

    public static Map<String, Object> invalidRoute() {

        Map<String, Object> search = new HashMap<>();

        search.put("from", "XXX");
        search.put("to", "YYY");
        search.put("date", LocalDate.now().plusDays(9).toString());
        search.put("class", "economy");
        search.put("pax", 1);

        return search;
    }

}