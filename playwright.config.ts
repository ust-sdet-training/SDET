import { defineConfig, devices } from "@playwright/test";

export default defineConfig({

    testDir: "./tests",

    timeout: 100_000,

    reporter: [
        ["list"],
        ["html"]
    ],

    use: {
        baseURL: "https://www.shoppersstop.com",
        headless: true,
        screenshot: "only-on-failure",
        trace: "on-first-retry"
    },

    projects: [
        {
            name: "chromium",
            use: {
                ...devices["Desktop Chrome"]
            }
        }
    ]
});