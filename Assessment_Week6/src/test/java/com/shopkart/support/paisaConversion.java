package com.shopkart.support;
public class paisaConversion {
    public static long toPaisa(String amount) {
        return Long.parseLong(
                amount.trim()
                        .replaceAll("[₹|Rs,\\s]", "")
                        .replace(".", "")
        );
    }
}
