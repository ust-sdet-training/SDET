import {Page} from "@playwright/test";
export class SearchPage{
    constructor(private page:Page){}

    async search(product:string){
        const searchBox=await this.page.getByPlaceholder("Search on Nykaa");
        await searchBox.click();
        await searchBox.fill(product);
        await this.page.keyboard.press('Enter');
    }

    async openfirstproduct(){
        await this.page.getByRole('link',{name:"Nykaa Lip Grip No-Transfer"})
    }
}