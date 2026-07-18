import dotenv from "dotenv";
import path from "path";
dotenv.config({
    path: path.resolve(__dirname, "../../../.env")
});

function required(key: string): string {
    const value = process.env[key];
    if (!value) {
        throw new Error(`Missing environment variable: ${key}`);
    }

    return value;
}

export const ENV = {
    baseUrl: required("BASE_URL"),
    browser: process.env.BROWSER ?? "chromium",
    headless: process.env.HEADLESS === "true",
    timeout: Number(process.env.TIMEOUT ?? 30000)

} as const;