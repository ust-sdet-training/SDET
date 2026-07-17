import dotenv from "dotenv";

dotenv.config();

export const user={
    username: process.env.TEST_USERNAME || "",
    password: process.env.TEST_PASSWORD || ""
}