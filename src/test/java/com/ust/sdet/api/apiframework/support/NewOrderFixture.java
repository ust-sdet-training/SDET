package com.ust.sdet.api.apiframework.support;

import java.util.List;
import java.util.Map;

public final class NewOrderFixture {
    private NewOrderFixture() {}

    public static Map<String, Object> newOrder() {
        return Map.of(
                "items", List.of(101, 106),
                "currency", "INR"
        );
    }
}
