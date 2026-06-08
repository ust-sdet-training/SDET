import {expect , type Page} from '@playwright/test'

export class Product{

    constructor(private readonly page:Page){}

    private add=()=>this.page
    .getByRole('button', {name:"Add to cart"})

    async prod(){
        const res= this.page.waitForResponse((r)=>
            r.url().includes('/api/items/cart') && r.status()===201 )

    
     await this.add().getByRole('button', {name:"Add to cart"}).click()
     await res 

     
    }
}
