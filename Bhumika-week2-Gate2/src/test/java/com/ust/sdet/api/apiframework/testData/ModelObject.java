package com.ust.sdet.api.apiframework.testData;

import java.util.List;
import java.util.Map;
public class ModelObject {

    public static Map<String, Object> createOrder() {

        return Map.of(
                "items", List.of(101,107),
                "currency","INR"
        );
    }
}


