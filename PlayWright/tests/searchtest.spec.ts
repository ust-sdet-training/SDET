// import {test} from '@playwright/test'
import {test} from '../fixtures/fixture'

test.describe("Search flow testing", async () => {
    test("Searching for a existing product", async ({shop}) => {
        await shop.positiveTest("Cosmetics")
    })

    test("Searching for non-existing product", async ({shop}) => {
        await shop.negativeTest("Mobikes")
    })
})