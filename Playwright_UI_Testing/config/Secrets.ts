import dotenv from "dotenv";

dotenv.config({ override: true });

export class Secrets {
    static get(name: string): string {
        const value = process.env[name];

        if (!value) {
            throw new Error(`Missing secret: ${name}`);
        }

        return value;
    }
}