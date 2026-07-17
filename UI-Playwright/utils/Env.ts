import dotenv from 'dotenv';

dotenv.config();

export class Env {

    static get(key: string): string {

        const value = process.env[key];

        if (!value) {
            throw new Error(`Missing env variable : ${key}`);
        }

        return value;
    }
}