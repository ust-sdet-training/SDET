import dotenv from "dotenv";
dotenv.config();

export class EnvCheck{
    static readonly BASE_URL = process.env.BASE_URL!

    static readonly TESTUSER_EMAIL = process.env.TEST_USER_EMAIL!
    static readonly TESTUSER_PASSWORD = process.env.TEST_USER_PASSWORD!

    static readonly TESTUSER_CARD_NUMBER = process.env.TEST_CARD_NUMBER!
    static readonly TESTUSER_CARD_EXPIRY = process.env.TEST_CARD_EXPIRY!
    static readonly TESTUSER_CARD_CVV = process.env.TEST_CARD_CVV!

}