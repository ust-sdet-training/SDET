package com.week4.provider;

public class CatalogController {

    public static String getProduct(String sku) {

        if ("SKU-1".equals(sku)) {
            return """
                    {
                      "sku":"SKU-1",
                      "availability":"IN_STOCK",
                      "priceMinor":129900
                    }
                    """;
        }

        return """
                {
                  "message":"Product not found"
                }
                """;
    }
}