package com.ust.capstone.data.db;

public final class Queries {

    private Queries() {
    }

    public static final String FIND_ORDER_BY_ID = """
            SELECT *
            FROM orders
            WHERE id = ?
            """;

    public static final String COUNT_PLACED_ORDERS_FOR_CUSTOMER = """
            SELECT COUNT(*)
            FROM orders
            WHERE customer_id = ?
            AND status = 'PLACED'
            """;

    public static final String FIND_ORDER_STATUS = """
            SELECT status
            FROM orders
            WHERE id = ?
            """;

    public static final String FIND_CART_TOTAL = """
        SELECT COALESCE(SUM(qty * unit_price_paise), 0)
        FROM cart_items
        WHERE cart_id = ?
        """;
}