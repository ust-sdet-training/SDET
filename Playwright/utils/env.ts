import dotenv from "dotenv";

dotenv.config();

export const config = {
  baseUrl: process.env.BASE_URL|| "",
  email: process.env.USER_EMAIL || "",
  password: process.env.PASSWORD || "",
  headless: process.env.HEADLESS === "false",
  cardName: process.env.CARD_NAME || "",
  cardNo: process.env.CARD_NO || "",
  expiry: process.env.EXPIRY || "",
  cvv: process.env.CVV || ""
};

const required = ["BASE_URL","USER_EMAIL","PASSWORD"];


for (const key of required) {
  if (!process.env[key]) {
    throw new Error(`Missing required environment variable: ${key}`);
  }
}