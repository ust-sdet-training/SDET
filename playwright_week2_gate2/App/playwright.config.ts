import { defineConfig, devices } from "@playwright/test";
 
export default defineConfig({
 
    testDir: "./tests",
 
 
    reporter: [
        ["list"],
        ["html"]
    ],
 
    use: {
        baseURL: "https://www.nykaa.com",
        headless: true,
        screenshot: "only-on-failure",
        trace: "on-first-retry",
        // actionTimeout : 1_000,a

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