import { test, expect } from '../fixtures/artifacts';

test("Prove offline banner",async ({page, context, log})=>{

  log.info("Moving to the url")
  await page.goto('/pos')
  await expect(page).toHaveURL(/\/pos/i)

  await expect.soft(page.getByTestId('network-banner')).toHaveText(/Online/i)

  log.info("Setting network offline")

  await context.setOffline(true)

  await expect.soft(page.getByTestId('network-banner')).toHaveText(/Offline/i)

  const online = await page.evaluate(()=>navigator.onLine)
  expect(online).toBe(false)

  await expect(navigator.onLine).toBeFalsy()

  var failedRequests = []

  log.info('Failing a syncing queued request')

  await page.route("**/api/sales", async(route, request)=>{
          await new Promise(resolve => setTimeout(resolve, 250));
          await route.abort("failed");
      });
      page.on("requestfailed", request=>{
          if(request.url().includes("/api/sales")){
              failedRequests.push(request.failure()?.errorText ?? "unknown failure");
          }
      });

      
  })