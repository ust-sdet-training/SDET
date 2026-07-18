import mysql from "mysql2/promise";

export async function getConnection() {
    return mysql.createConnection({
        host: process.env.DB_HOST || "localhost",
        port: Number(process.env.DB_PORT || 3306),
        user: process.env.DB_USERNAME || "root",
        password: process.env.DB_PASSWORD || "root@123",
        database: process.env.DB_NAME || "tripstack"
    });
}