export class Config {

    static readonly baseUrl =
        process.env.BASE_URL ?? "https://tripstack.doomple.com/";

    static readonly defaultTimeout = 30000;

    static readonly navigationTimeout = 60000;

    static readonly headless =
        process.env.HEADLESS === "true";

}