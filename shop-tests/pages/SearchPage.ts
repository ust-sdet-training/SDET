import { expect, type Page} from '@playwright/test'

export class SearchPage {

    constructor(private readonly page: Page){}

    private box = ()=> this.page
        .getByRole('searchbox')
    
    results = ()=> this.page
        .getByTestId('catalog-result-count')

    card =()=> this.page
    .locator('.product-grid')

    
    
    async Search(q: string){
        const res = this.page.waitForResponse(
            (r)=> r.url().includes('/products') && r.status()===200)
    
        await this.box().fill(q)
        await this.box().press("Enter")
        // await this.box().click()
        await res
        
        // await this.card().getByRole('link', {name:/view/i}).first().click()
        
    }
    

}   