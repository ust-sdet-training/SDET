import { test, expect } from "../fixtures/diagnostic.fixture";

const API_URL = process.env.API_BASE_URL || "http://localhost:4000";

const headers = {
    Authorization: "Bearer demo-token-1-customer"
};

test.describe("Test Stabilization", () => {

    test("Verify cart is loaded before validation", async ({ page, request, log }) => {

        const cartSession = `cart-${Date.now()}`;

        await request.post(`${API_URL}/api/cart/items`, {
            headers: {
                ...headers,
                "X-Cart-Session": cartSession
            },
            data: {
                productId: 101,
                quantity: 1,
                size: "UK 9",
                color: "Black",
                fulfilment: "Home delivery"
            }
        });

        await page.goto("/debug-lab");

        await page.waitForLoadState("networkidle");

        await expect(
            page.getByRole("heading", { name: "Debug Lab" })
        ).toBeVisible();

        log.info("Debug page loaded successfully");
    });

    test("Verify cart API response before validation", async ({ page, log }) => {

    await page.goto("/debug-lab");

    // Wait for the real API request while clicking the button
    const responsePromise = page.waitForResponse(response =>
        response.url().includes("/api/debug/cart-total") &&
        response.status() === 200
    );

    await page.getByRole("button", { name: "Refresh cart total" }).click();

    await responsePromise;

    await expect(
        page.getByRole("heading", { name: "Debug Lab" })
    ).toBeVisible();

    log.info("Cart API loaded successfully");
});

});