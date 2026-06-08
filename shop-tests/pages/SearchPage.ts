// import type{ Page,expect } from "@playwright/test";
// export class SearchPage{
//     constructor(private readonly page:Page) {}
//     private box=()=> this.page.getByRole('searchbox');
//     results=()=> this.page.getByTestId('catalog-result-count');
//     async open(){
//         await this.page.goto('/catalog');
//     }
//     async search (q:string){
//         const res=this.page.waitForResponse(r=>r.url().includes('/search') && r.status()===200);
//         await this.box().fill(q);
//         await this.box().press('Enter');
//         await res;
//       }  }

import { type Page,expect } from "@playwright/test";

// export class SearchPage {

//     constructor(private readonly page: Page) {}
//     private box = () => this.page.getByRole("searchbox");
//     results = () => this.page.getByTestId("catalog-result-count");
//     async open() {
//         await this.page.goto("/catalog");
//     }
//     async search(productName: string) {
//         await this.box().fill(productName);
//         await this.page.getByRole("button", {name: "Search"}).click();

    
//     }
// }
 
export class SearchPage {
    constructor(private page: Page) {}
    private box = () => this.page.getByRole('searchbox');
    private searchButton = () => this.page.getByRole('button',{name:'Search'});
    results = () => this.page.getByTestId('catalog-result-count');
   
    async search(q: string) {
        // const res = this.page.waitForResponse(
        //     r => r.url().includes('products?search=') && r.status() === 200
        // );
        await this.box().fill(q);
        await this.box().press('Enter');
        // await res;
    }
 
//     async searchNoResults(query:string){
//         await this.box().fill(query);
//         await expect(this.box()).toHaveValue(query);
//         await Promise.all([
//             this.page.waitForResponse(r =>r.url().includes('/products') && r.status()===200),
//             this.searchButton().click()
//         ]);
//         await expect(this.results()).not.toContainText(query);
//     }
// }
async searchNoResults(query: string) {
    await this.box().fill(query);
    await Promise.all([this.page.waitForResponse(response =>response.url().includes('/api/products') && response.status() === 200),
        this.searchButton().click()]);
        await expect(this.results()).toContainText('0');
}
}