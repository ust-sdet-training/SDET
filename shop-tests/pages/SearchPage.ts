import { expect, type Page } from "@playwright/test";

export class SearchPage {
  constructor(private readonly page: Page) {}

  private inputform = ()=>this.page.getByRole('form',{name:"Product filters"})
  private box = ()=>this.inputform().getByRole('searchbox',{name:/Search Products/i})

  private searchbutton = ()=>this.inputform().getByRole('button',{name:/Search/i})

  private results = ()=> this.page.getByTestId('product-card')

  private addCartButton = ()=>this.page.getByRole('button',{name:/add to cart/i})

  async gettotal(){
    return await this.results()
  }

  async search(query : string) {


    const response = this.page.waitForResponse( (response)=>{

        return response.url().includes('/product') && 
        response.status()===200 &&
        new URL(response.url()).searchParams.get('search')===query
    })

    await this.box().fill(query)
    await this.searchbutton().click()

    //await this.box().press('Enter')

    await response

  }

   async checkitems(query : string, count : number) {

    await this.search(query)
    
    //await expect.soft(await this.gettotal()).toHaveCount()


    // const response = 


  }

  
  async expectError(message: string) {
    await expect(this.page.getByRole("alert")).toContainText(message);
  }
}
