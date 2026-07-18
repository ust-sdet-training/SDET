package api.database;

public final class SqlQueries {

    private SqlQueries() {
    }

    public static final String FIND_BOOKING_TABLE_BY_NAME = """
                    SELECT table_name
                    FROM information_schema.tables
                    WHERE table_schema = DATABASE()
                        AND (
                            LOWER(table_name) LIKE '%booking%'
                            OR LOWER(table_name) LIKE '%book%'
                            OR LOWER(table_name) LIKE '%reservation%'
                            OR LOWER(table_name) LIKE '%ticket%'
                            OR LOWER(table_name) LIKE '%order%'
                            OR LOWER(table_name) LIKE '%hold%'
                        )
                    ORDER BY
                        CASE
                            WHEN LOWER(table_name) LIKE '%booking%' THEN 1
                            WHEN LOWER(table_name) LIKE '%reservation%' THEN 2
                            WHEN LOWER(table_name) LIKE '%ticket%' THEN 3
                            WHEN LOWER(table_name) LIKE '%hold%' THEN 4
                            WHEN LOWER(table_name) LIKE '%order%' THEN 5
                            ELSE 6
                        END,
                        table_name
                    LIMIT 1
                    """;

    public static final String FIND_BOOKING_TABLE_BY_COLUMNS = """
                    SELECT table_name
                    FROM information_schema.columns
                    WHERE table_schema = DATABASE()
                        AND LOWER(column_name) IN (
                                'pnr',
                                'booking_reference',
                                'bookingref',
                                'booking_id',
                                'bookingid',
                                'state',
                                'empid',
                                'emp_id',
                                'inventoryid',
                                'inventory_id',
                                'seatids',
                                'seat_ids',
                                'amountpaise',
                                'amount_paise',
                                'refundable',
                                'holdexpiresat',
                                'hold_expires_at'
                        )
                    GROUP BY table_name
                    ORDER BY COUNT(*) DESC, table_name
                    LIMIT 1
            """;

    public static String findBookingByPnr(String tableName, String pnrColumn) {
        return "SELECT * FROM `" + tableName + "` WHERE `" + pnrColumn + "` = ? LIMIT 1";
    }

    public static String findColumnByKeywords(int keywordCount) {
        String conditions = String.join(" OR ", java.util.Collections.nCopies(
                keywordCount, "LOWER(column_name) LIKE ?"));

        return "SELECT column_name FROM information_schema.columns "
                + "WHERE table_schema = DATABASE() AND table_name = ? AND ("
                + conditions + ") ORDER BY column_name LIMIT 1";
    }
}
