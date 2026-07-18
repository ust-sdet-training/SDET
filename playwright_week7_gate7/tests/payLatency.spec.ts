import { expect } from '@playwright/test'
import { test } from '../fixtures/e2eFixture'
import { environment } from '../config/environment'

test("Payment latency Handling", async ({ page, login, flight, search, available, seat, pass, book, pay, confirm, log }) => {
  log.info("Payment Resiliency")

  await login.login(environment.email, environment.password)
  await flight.search()
  await search.search("DEL", "BLR", "First", "Friday, 17 July")
  await available.available()
  await seat.seat("1A")
  await pass.passengerselect(
    environment.firstname,
    environment.lastname,
    "23",
    "Male",
    environment.email,
    environment.number
  )

  await page.route("**/api/payment", async route => {
    log.info("payment latency added")

    await new Promise(resolve => setTimeout(resolve, 5000))
    await route.continue()
  })

  await pay.pay(environment.cardname, environment.cardnumber, environment.expiry, environment.cvv)

  await confirm.confirm()

 await expect(page.locator('[data-id="state"]').first()).toHaveText("CONFIRMED", { timeout: 5000 })

})