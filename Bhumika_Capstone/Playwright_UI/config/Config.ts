import dotenv from "dotenv";

dotenv.config();

export class Config {
    static readonly baseUrl = process.env.BASE_URL || "https://tripstack.doomple.com";
    static readonly browser = (process.env.BROWSER || "chromium") as "chromium" | "firefox" | "webkit";
    static readonly timeout = Number(process.env.TIMEOUT) || 30000;
    static readonly headless = process.env.HEADLESS === "true";
}