import { expect } from '@playwright/test'
import { test } from '../fixtures/e2eFixture'
import { environment } from '../config/environment'



test('Flight search performance', async ({ login, flight, search }) => {
  await login.login(environment.email, environment.password)

  const start = Date.now()
  await flight.search()
  await search.search('DEL', 'BLR', 'First', 'Friday, 17 July')
  const elapsedMs = Date.now() - start

  console.log(`Flight search took ${elapsedMs}ms`)
  expect(elapsedMs).toBeLessThan(30000)
})