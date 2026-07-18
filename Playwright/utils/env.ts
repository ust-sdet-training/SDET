import dotenv from "dotenv";

dotenv.config();

export const config = {
  baseUrl: process.env.BASE_URL|| "",
  email: process.env.PREM_TRIPSTACK_EMAIL || "",
  password: process.env.PREM_TRIPSTACK_PASSWORD || "",
  headless: process.env.HEADLESS === "false",
  cardName: process.env.CARD_NAME || "",
  cardNo: process.env.CARD_NO || "",
  expiry: process.env.EXPIRY || "",
  cvv: process.env.CVV || ""
};

const required = ["BASE_URL","PREM_TRIPSTACK_EMAIL","PREM_TRIPSTACK_PASSWORD"];


for (const key of required) {
  if (!process.env[key]) {
    throw new Error(`Missing required environment variable: ${key}`);
  }
}