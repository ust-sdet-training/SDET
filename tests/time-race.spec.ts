import { expect, test } from "../fixtures/log-and-evidence";
import { PosPage } from "../pages/PosPage";

test.describe("Network debugging", () => {
    test.only("Race Condition Test: ", async ({ page, log, context }) => {
      const posPage = new PosPage(page);

      await page.route("**/api/sales", async (route)=>{
        const random = Math.random();
        if(random<0.10){
          const delay = 100 + Math.random() * 100;
          log.warn(`API is slow; Expected Delay ${delay} ms`)
          await new Promise(resolve => setTimeout(resolve, delay));
          await route.continue();
        }else{
          log.warn("Simulated correct API response");
          await route.continue();
          console.log(random);
        }
      })

      log.info("Pos Page is opening")
      posPage.goToPosPage();

      await expect(page).toHaveURL("/pos");
      log.info("Pos Page URL is checked...")

      log.info("Status Banner's visibility and text is being verified when app has online status");
      await expect.soft(await posPage.statusBanner()).toBeVisible();
      await expect.soft(await posPage.statusBanner()).toContainText(/Online/i);

      log.info("'Reset Lab' button is clicked to empty Local Outbox")
      await page.getByRole("button", {name: "Reset lab"}).dblclick();

      log.info("Local Outbox's visibility and empty message are being verified")
      await expect.soft(page.locator('.pos-outbox')
      .getByRole('status')).toBeVisible();
      await expect.soft(page.locator('.pos-outbox')
      .getByRole('status')).toHaveText(/No queued sales./i);

      log.info("'Queue sale' button is clicked");
      await page.getByRole("button", {name: "Queue sale"}).click();

      log.info("Pending counts increase is being verified");
      await expect.soft(page.getByTestId("outbox-count")).toHaveText("0", { timeout: 10000 });

      log.info("New sale is being synced...");
      await expect(page.getByTestId("outbox-status")).toHaveText("synced", {timeout: 10000});
    });


    test("Cart total is off by a Paisa", async ({ page, log, context }) => {
      const posPage = new PosPage(page);

    });


})