import {test,expect} from '@playwright/test'
import {LoginPage} from '../pages/LoginPage'
import {testUsers} from '../fixtures/test-users'
import {SearchPage} from'../pages/SearchPage'
test.describe("From login to ProductCart",()=>{

    test("login page to the cart",async({page})=>{
    await page.goto('/');
    await expect(page.getByRole("heading",{name:"SDET Retail Automation Lab"})).toBeVisible()
    await page.getByRole('link',{name:"Sign In"}).click()
    await expect(page).toHaveURL('/login')
    await expect(page.getByRole("heading",{name:"Sign in to Retail Lab"})).toBeVisible();
    const lp=new LoginPage(page)
    await lp.login(testUsers.customer.email,testUsers.customer.password)
    page.goto('/login')
    await lp.inlogin(testUsers.invalid.email,testUsers.invalid.password)
})
    test("Search Product in the cart",async({page})=>{
        await page.goto('/catalog');
        await expect(page.getByRole("heading",{name:'Product Catalog'})).toBeVisible()
        const prod_name=new SearchPage(page)
        prod_name.search("Shoes")
        await expect(prod_name.card()).toBeVisible()
        prod_name.click_button()
        

    })
    


})


