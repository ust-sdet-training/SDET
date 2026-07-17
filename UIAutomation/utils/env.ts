import dotenv from 'dotenv';

dotenv.config();

export const ENV = {
    BASE_URL: process.env.BASE_URL!,
    EMAIL: process.env.EMAIL!,
    PASSWORD: process.env.PASSWORD!,
    EMP_ID: process.env.EMP_ID!
};