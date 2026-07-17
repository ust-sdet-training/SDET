export const ENV = {
  baseUrl: process.env.BASE_URL ?? "https://tripstack.doomple.com",
  timeout: Number(process.env.PW_TIMEOUT ?? 120000),
  browser:
    (process.env.PW_BROWSER as "chromium" | "firefox" | "webkit") ?? "chromium",
  headless: process.env.PW_HEADLESS ? process.env.PW_HEADLESS === "true" : true,
  userEmail: process.env.TRIPSTACK_USER_EMAIL ?? "xavier@tripstack.test",
  userPassword: process.env.TRIPSTACK_USER_PASSWORD ?? "Password@123",
};
