import { doesNotMatch } from "node:assert";
import { test, expect } from "../fixtures/artifact-tests";
import { resolve } from 'node:dns';

const outboxKey = "sdet-retail-pos-outbox";
 
async function openCleanPost(page) {
    await page.addInitScript((key) => window.localStorage.removeItem(key), outboxKey);
    await page.goto("http://localhost:5173/pos");
    await expect(page.getByRole("heading",{name: "Resilient POS"})).toBeVisible();
}
 
function saleResponse(clientSaleId: string){
    return{
        id:9101,
        saleNumber: "SALE-W5D4-9101",
        clientSaleId,
        productId: 101,
        productName: "Running Shoes",
        quantity: 1,
        total: 4499,
        status: "SYNCED"
    };
}

test.describe('Testing Resiliency',()=>{

    test("rolls back an online optimistic sale when the sync request fails", async({page,evidence})=>{
        const failedRequests: string[] = [];
        await page.route("**/api/sales", async(route, request)=>{
            await new Promise(resolve => setTimeout(resolve, 250));
            await route.abort("failed");
        });
        page.on("requestfailed", request=>{
            if(request.url().includes("/api/sales")){
                failedRequests.push(request.failure()?.errorText ?? "unknown failure");
            }
        });
 
        await openCleanPost(page);
        evidence.logs?.push("POS page opened");
        await page.getByRole("button", {name: "Queue sale"}).click();
        evidence.logs?.push("Sale queued");
 
        const pendingRow = page.getByTestId("outbox-row");
        await expect(pendingRow).toHaveAttribute("data-pending", "true");
        await expect(page.getByTestId("outbox-status")).toHaveText("pending");
        await expect(pendingRow).toHaveCount(0);
        await expect(page.getByTestId("outbox-count")).toHaveText("0");
        await expect(page.getByTestId("pos-sync-status")).toHaveText(
            "Sync failed. Optimistic sale rolled back."
        );
        evidence.logs?.push("Sync failed");
        expect(failedRequests.length, "failed sale sync request should be observable").toBeGreaterThan(0);

        evidence.diagnosis = `Rollback successful.
        Expected: Sync request failed.
        Actual:Optimistic row removed.
        Result:Rollback PASS.`;
    });


    test('queued sale retries with same idempotency key', async ({ page, context,evidence }) => {
    let attempts = 0;
    let successfulPosts = 0;
    const keys = new Set<string>();
 
    await page.route('**/api/sales', async route => {
        attempts++;
        const key = await route.request().headerValue('Idempotency-Key');
        expect(key).toBeTruthy();
        keys.add(key!);
 
        if (attempts === 1) {
            await route.abort();
            return;
        }
 
        successfulPosts++;
 
        await route.fulfill({
            status: 201,
            contentType: 'application/json',
            body: JSON.stringify({
                saleId: 'sale-123',
            })
        });
    });
 
    await page.goto('http://localhost:5173/pos');
    evidence.logs?.push("POS opened");
    await context.setOffline(true);
    evidence.logs?.push("Offline");
    await page.getByRole('button', { name: 'Queue sale' }).click();
    evidence.logs?.push("Sale queued");
    await expect(page.getByTestId('outbox-count')).toHaveText('1');
    await context.setOffline(false);
    evidence.logs?.push("Back online");
    await expect(page.getByTestId('outbox-count')).toHaveText('0');
    expect(attempts).toBe(2);
    expect(successfulPosts).toBe(1);
    expect(keys.size).toBe(1);

    evidence.diagnosis = `Retry completed.
    Attempts : ${attempts}
    Successful Posts : ${successfulPosts}
    Idempotency Key reused : ${keys.size}PASS`;
});

 
  test("detects and displays offline banner", async ({ page, context ,evidence}) => {
    await openCleanPost(page);
    evidence.logs?.push("POS opened");
    await expect(page.getByTestId("network-banner")).toContainText(/online/i);
    await context.setOffline(true);
    evidence.logs?.push("Browser offline");
    //This is wrong because the application is actually Offline i change to online so it will fail
    //After i traced i seen the error and changed to affline so it is passing
    await expect(page.getByTestId("network-banner")).toContainText(/offline/i);
    const online = await page.evaluate(() => navigator.onLine);
    expect(online).toBe(false);
    await context.setOffline(false);
    evidence.diagnosis = `Offline banner displayed.
    navigator.onLine = false 
    PASS`;
});

   test("queues a sale while offline", async ({ page, context ,evidence}) => {
    await openCleanPost(page);
    evidence.logs?.push("POS opened");
    await context.setOffline(true);
    await page.getByRole("button", { name: "Queue sale" }).click();
    evidence.logs?.push("Sale queued offline");
    const row = page.getByTestId("outbox-row");
    await expect(row).toHaveAttribute("data-pending", "true");
    await expect(page.getByTestId("outbox-status")).toHaveText(/pending/i);
    await expect(page.getByTestId("outbox-count")).toHaveText("1");
    evidence.diagnosis = `Sale stored in outbox.
    Pending = true
    PASS`;
});

   test("syncs queued sale on reconnect", async ({ page, context,evidence }) => {
      let posts = 0;

    await page.route("**/api/sales", async route => {
        posts++;

        await route.fulfill({
            status: 201,
            contentType: "application/json",
            body: JSON.stringify(saleResponse("client-1"))
        });
    });

    await openCleanPost(page);
    evidence.logs?.push("POS page opened");
    await context.setOffline(true);
    evidence.logs?.push("Application switched to Offline mode");
    await page.getByRole("button", { name: "Queue sale" }).click();
    evidence.logs?.push("Sale queued successfully");
    await expect(page.getByTestId("outbox-count")).toHaveText("1");
    evidence.logs?.push("Outbox contains 1 queued sale");
    await context.setOffline(false);
    evidence.logs?.push("Application switched back Online");
    await expect(page.getByTestId("outbox-count"))
        .toHaveText("0");
        evidence.logs?.push("Queued sale synchronized successfully");

    expect(posts).toBe(1);
    evidence.diagnosis = `Offline Sync Test
    POS opened successfully.
    Application switched Offline.
    Sale queued successfully.
    Application switched Online.
    Queued sale synchronized successfully.
    Result : PASS`;
});

  test("creates evidence for offline to sync journey", async ({ page, context }, testInfo) => {
    const evidence = [];
    evidence.push("POS opened");
    await openCleanPost(page);
    await context.setOffline(true);
    evidence.push("Offline");
    await page.getByRole("button", {
        name: "Queue sale"
    }).click();
    evidence.push("Queued");
    await expect(page.getByTestId("outbox-count")).toHaveText("1");
    await context.setOffline(false);
    evidence.push("Online");
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    evidence.push("Synced");
    await testInfo.attach(
        "offline-sync-journey.txt",
        {
            body: evidence.join("\n"),
            contentType: "text/plain"
        }
    );

});

  
});

