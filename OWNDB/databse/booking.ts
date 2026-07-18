import { getConnection } from "./db";

export async function getBooking(pnr: string) {


    const con = await getConnection();

    const [rows] = await con.execute(
        "SELECT * FROM bookings ORDER BY id DESC LIMIT 1"
    );

    await con.end();

    return rows;
}
