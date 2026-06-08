    import {test, expect} from '@playwright/test'
    import { LoginPage} from '../pages/LoginPage'
    import { SearchPage} from '../pages/SearchPage'
    import {Product} from '../pages/ProductPage'
    import {testUsers} from '../fixtures/test-Users.ts'
    
    test.describe("Login To Add To Cart", ()=>{

        test("Sign in Failed" , async({page})=>{

            await page.goto('/login')

            await expect(page.getByRole('heading', {name:"Sign in to Retail Lab"})).toBeVisible()

            const lp = new LoginPage(page)
            await lp.inlogin(testUsers.invalid.email, testUsers.invalid.password)
            
        })

        
        test("Sign in" , async({page})=>{

            await page.goto('/login')

            await expect.soft(page.getByRole('heading', {name:"Sign in to Retail Lab"})).toBeVisible()

            const lp = new LoginPage(page)
            await lp.login(testUsers.customer.email, testUsers.customer.password)
            
        })

        test("Search Product in the cart" , async({page})=>{

            await page.goto('/catalog')

            await expect.soft(page.getByRole('heading', {name:"Product Catalog"})).toBeVisible()

            const s = new SearchPage(page)
            await s.Search('runn')
            await expect(s.results()).toBeVisible()
            
            
        })
        
         test("Add to Cart" , async({page})=>{

            await page.goto('/cart')

            await expect.soft(page.getByRole('heading', {name:"Cart", level:1})).toBeVisible()

            const p = new Product(page)
            
            
            
        })

        test("runs Axe accessibility", async ({ page }) => {
        for (const path of ["/catalog", "/product/running-shoes", "/cart"]) {
            await page.goto(path)

            const accessibilityScanResults = await new AxeBuilder({ page })
                .disableRules([])
                .analyze()

            expect(accessibilityScanResults.violations).toEqual([])
        }
    })

    })