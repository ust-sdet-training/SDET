import {test,expect} from '@playwright/test'
import { LoginPage } from '../pages/LoginPage'
import {testUsers } from '../fixtures/test-users'
import { HomePage } from '../pages/HomePage'
import { ProductPage } from '../pages/ProductPage'
import { SearchPage } from '../pages/SearchPage'
import { CartPage } from '../pages/CartPage'
import { CheckoutPage } from '../pages/CheckoutPage'
import { OrdersPage } from '../pages/OrdersPage'

test.describe("Week 1 Assessment",()=>{
    test("Full check",async({page})=>{

        await page.goto('/')

        const loginpage = new LoginPage(page)

        const homepage = new HomePage(page)

        const searchpage = new SearchPage(page)

        const productpage = new ProductPage(page)

        const cartpage = new CartPage(page)

        const checkoutpage = new CheckoutPage(page)

        const orderspage = new OrdersPage(page)

        await loginpage.goto()

        await loginpage.login(testUsers.customer.email,testUsers.customer.password)

        await loginpage.success()

        await homepage.gotoProducts()

        //await searchpage.checkURL()
        await searchpage.search('run')

        await (await searchpage.gettotal()).first().getByRole('link').click()

        await productpage.checkURL()

        await productpage.addToCart()

        await cartpage.proceedToCheckout()

        await checkoutpage.placeOrder()

        //await checkoutpage.checkorderid('Order')

        await orderspage.verifyorders(checkoutpage)

    })

    test("Login Failure",async ({page})=>{

        const loginpage = new LoginPage(page)
        await loginpage.goto()
        await loginpage.login(testUsers.customer.email,'Test')
        await loginpage.failure()
        await loginpage.expectError('Invalid credentials')
    })

    test("Quantity exceeded",async ({page})=>{

        
    })
})
