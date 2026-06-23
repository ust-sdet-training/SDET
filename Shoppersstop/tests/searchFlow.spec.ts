import {test, expect} from '@playwright/test'

test("Verifying home page and searching product", async ({page}) => {
    // let logo = page.getByAltText("logo")
    let search = page.locator("input")
    await page.goto("https://www.shoppersstop.com/")
    await expect(page).toHaveTitle(/Shoppers Stop/)
    // await expect(page.getByTestId("HeroBanner-single-card-component")).toBeInViewport()
    await expect(search).toBeVisible()
    await search.fill("Watches")
    await search.press("Enter")
    await expect(page.getByText("Watches").first()).toBeVisible()
    // await expect(page).toHaveURL(/Watches/)
    
    
})

test("Verifying home page and searching non-existing product", async ({page}) => {
    // let logo = page.getByAltText("logo")
    let search = page.locator("input")
    await page.goto("https://www.shoppersstop.com/")
    await expect(page).toHaveTitle(/Shoppers Stop/)
    // await expect(page.getByTestId("HeroBanner-single-card-component")).toBeInViewport()
    await expect(search).toBeVisible()
    await search.fill("Mobikes")
    await search.press("Enter")
    await expect(page.getByText("No Result Found")).toBeVisible()
    // await expect(page).toHaveURL(/Watches/)
    
    
})