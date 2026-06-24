import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
    testDir: "./tests",
    timeout: 10_000,

    reporter: [
        ["list"],
        ["html"]
    ],

    use: {
        baseURL: "https://www.nykaa.com/",
        headless: false,
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