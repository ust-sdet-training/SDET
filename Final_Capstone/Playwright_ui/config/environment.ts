import dotenv from 'dotenv';

dotenv.config();

export class Environment {

    // Application

    static readonly baseUrl = process.env.BASE_URL!;

    // Login

    static readonly username = process.env.USER_NAME!;

    static readonly password = process.env.PASSWORD!;

    // Passenger Details

    static readonly firstName = process.env.FIRST_NAME!;

    static readonly lastName = process.env.LAST_NAME!;

    static readonly age = process.env.AGE!;

    static readonly gender = process.env.GENDER!;

    static readonly phone = process.env.PHONE!;

    // Payment

    static readonly cardName = process.env.CARD_NAME!;

    static readonly cardNumber = process.env.CARD_NUMBER!;

    static readonly expiry = process.env.CARD_EXPIRY!;

    static readonly cvv = process.env.CARD_CVV!;

    // Browser

    static readonly browser =
        process.env.BROWSER || "chromium";

    static readonly headless =
        process.env.HEADLESS === "true";

    static readonly defaultTimeout =
        Number(process.env.DEFAULT_TIMEOUT || 30000);

    static readonly expectTimeout =
        Number(process.env.EXPECT_TIMEOUT || 10000);

}