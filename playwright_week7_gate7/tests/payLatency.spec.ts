import { expect } from '@playwright/test'
import { test } from '../fixtures/e2eFixture'
import { environment } from '../config/environment'

test("Payment latency Handling", async ({ page,login,flight,search,available,log}) => 
{
  log.info("Payment Resiliency")

  await login.login(environment.email, environment.password)
  await flight.search()
  await search.search("DEL", "BLR", "First", "Friday, 17 July")
  await available.available()

  await page.route("**/api/payment", async route => {
    log.info("payment latency")

    await new Promise(resolve => setTimeout(resolve, 5000))
    await route.continue()
  })

})