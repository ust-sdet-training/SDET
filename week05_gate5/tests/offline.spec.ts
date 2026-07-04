import {test,expect} from "../fixtures/app.fixture";
import {PosPage} from "../pages/pospage" 
test("Offline flow for sale queue", async({page,evidence,context, log}) => {
    let attempts=0;
    let Post=0;
    const keys = new Set<string>();
    await page.route("**/api/sales", async (route) => {
      if (route.request().method() !== "POST") 
        {
        return route.continue();
        }
      attempts++;
      const key = route.request().headers()["idempotency-key"];
      log.info("Network set to offline attempting post", 
        {
        attempt: attempts,
        idempotencyKey: key
      });

      expect(key).toBeTruthy();
      keys.add(key!);
 
      if (attempts === 1) 
        {

        log.warn("Simulating failure on first sync attempt");
        await route.abort("failed");

      } else {
        Post++;
        log.info("Allowing retry to succeed");
        await route.continue();
      }
 
    });
    
    const pospage=new PosPage(page);
    log.error("go to page after load")
    evidence.offlinePage=pospage
    await pospage.goto();
    log.info("Navigated to the POS page");
    await context.setOffline(true);
    log.info("Network switched off");
    await expect(await pospage.getbanner()).toHaveText("Offline - sales will be queued");
    await page.getByRole("button", { name: "Queue sale" }).click();
    await context.setOffline(false);
    log.info("Network restored");
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    log.info("Verified outbox flushed");
    expect(attempts).toBe(2);
    expect(Post).toBe(1);
    expect(keys.size).toBe(1);
    log.info("verifying post exactly once", {
      attempts,
      Post,
      uniqueIdempotencyKeys: keys.size
    });
})
 