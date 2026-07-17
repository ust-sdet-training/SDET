import dotenv from "dotenv";

dotenv.config();

export const env = {
    baseUrl: process.env.BASE_URL!,
    email: process.env.EMAIL!,
    password: process.env.PASSWORD!
};