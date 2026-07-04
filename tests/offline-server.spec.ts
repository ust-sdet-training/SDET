import {test, expect} from "@playwright/test"

//Checks if offline banner is visible when network is off and asserts the outbox count
test("offline banner", async({page, context}) =>{
    await page.goto("/pos")
    await context.setOffline(true)
    await expect(page.getByTestId('network-banner')).toContainText('Offline');

     expect(await page.evaluate(() => navigator.onLine)).toBe(false);
        await context.setOffline(false);
        await expect(page.getByTestId('network-banner')).toContainText('Online');

        await context.setOffline(true);
        await page.getByRole("button", {name: "Queue sale"}).click();
        await page.getByRole("button", {name: "Queue sale"}).click();

        await expect(page.getByTestId('outbox-count')).toHaveText('2');
        await context.setOffline(false);
        await expect(page.getByTestId('outbox-count')).toHaveText('0');
});




test("order confirmation request fails", async ({ page }) => {

  await page.route("**/api/orders", async route => {
    await route.abort("failed");
  });

  await page.goto("/login");
  await page.getByLabel("Email").fill("customer@example.com");
  await page.getByLabel("Password").fill("Password@123");
  await page.getByRole("button", { name: "Sign in" }).click();
  await expect(page).toHaveURL(/home/);

  await page.goto("/catalog");
  await page.getByRole("link", { name: "View Running Shoes" }).click();
  await page.getByRole("button", { name: "Add to cart" }).click();
  await page.getByRole("button", { name: "Proceed to checkout" }).click();

  await expect(page.getByRole("button", { name: "Place order" })).toBeVisible();

  await page.getByRole("button", { name: "Place order" }).click();

  await expect(page.getByRole("alert")).toContainText("Order service failed");
});


test('Rollback', async ({ page }) => {
        await page.route('**/api/sales', async (route) => {
            await new Promise(resolve => setTimeout(resolve, 250));
            await route.abort('failed');
        });
        await page.goto('/pos');
        await page.getByRole('button', { name: 'Queue sale' }).click();
        await expect(page.getByTestId('outbox-status')).toHaveText('pending');
        await expect(page.getByTestId('outbox-count')).toHaveText('0');
    });
 
 
    test('sync once, even through a flaky reconnect', async ({ page, context }) => {
        let attempts = 0;
        let successPosts = 0;
        const keys = new Set<string>();
        await page.route('**/api/sales', async (route) => {
            if (route.request().method() !== 'POST') {
            return route.continue();
            }
            attempts++;
            const key = route.request().headers()['idempotency-key'];
            expect(key).toBeTruthy();
            keys.add(key!);
            if (attempts === 1) {
                await route.abort('failed');
            }
            else {
                successPosts++;
                await route.continue();
            }
        });
 
        await page.goto('/pos');
        await context.setOffline(true);
        await page.getByRole("button", {name: "Queue sale"}).click();
        await context.setOffline(false);
        await expect(page.getByTestId('outbox-count')).toHaveText('0');
        expect(attempts).toBe(2);      
        expect(successPosts).toBe(1);  
        expect(keys.size).toBe(1);    
    });