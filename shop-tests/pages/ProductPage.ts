import { expect, type Page } from "@playwright/test";


export class ProductPage {

     private addCartButton = ()=>this.page.getByRole('button',{name:/add to cart/i})
constructor(private readonly page: Page) {}

    async checkURL(){
        expect.soft(this.page).toHaveURL(/\/product/)
    }

    async addToCart(){

    //await this.results().first().getByRole('link').click()

    const response = this.page.waitForResponse((response)=>{
      return response.url().includes('/cart') &&
      response.request().method()==='GET'&&
        [200,201].includes(response.status())
    })



    await this.addCartButton().click()

    await response

  }

}