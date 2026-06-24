import {test} from '@playwright/test'
import {testing} from '../fixtures/fixture'

test.describe("Search flow testing", async () => {
    testing("Searching for a existing product", async ({shop}) => {
        await shop.positiveTest("Cosmetics")
    })

    testing("Searching for non-existing product", async ({shop}) => {
        await shop.negativeTest("Mobikes")
    })
})