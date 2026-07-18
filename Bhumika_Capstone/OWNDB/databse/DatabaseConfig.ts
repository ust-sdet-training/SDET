import mysql from "mysql2/promise";
export async function getConnection() {
    return await mysql.createConnection({   
        host: "localhost",
        user: "root",
        password: "root@123",
        database: "tripstack"
    });
}