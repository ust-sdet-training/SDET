import { defineConfig, devices } from "@playwright/test";
const CI = !!process.env.CI;
 import  dotenv from "dotenv";
dotenv.config();
export default defineConfig({
    testDir:"./tests",
    timeout: 10_000,
    fullyParallel: true,
    retries: CI ? 2:0,
    reporter: CI ? [["list"], ["blob"], ["html", {open: "never"}]] : [["html", {open: "never"}]],
    use:{
        baseURL: "https://tripstack.doomple.com/",
        screenshot: CI ? "only-on-failure" : "on",
        trace: CI ? "on-first-retry" : "off",
        video: CI ? "retain-on-failure" : "off"
        
    },
    projects:[
        {
            name: "chromium",
            use: {...devices["Desktop Chrome"]}
        }
    ]
});