import { Page, expect } from "@playwright/test";

export class SearchPage {

    constructor(readonly page: Page){}

    private searchBox =this.page.getByLabel('Search products');

    private searchButton =this.page.getByRole('button',{name:'Search'});

    private results =this.page.getByLabel('Product results');

    async open(){
        await this.page.goto('/catalog');
        await expect(this.page).toHaveURL('/catalog');
    }

    async search(query:string){
        await this.searchBox.fill(query);
        await expect(this.searchBox).toHaveValue(query);
        await Promise.all([ 
            this.page.waitForResponse(r =>r.url().includes('/products') && r.status()===200 ),
            this.searchButton.click()
        ]);
        await expect(this.results).toBeVisible();
    }

    async openProduct(product:string){
        await this.results.getByRole('link',{name:`View ${product}`}).click();
        await expect(this.page).toHaveURL(/\/product/);
    }

    async searchNoResults(query:string){
        await this.searchBox.fill(query);
        await expect(this.searchBox).toHaveValue(query);
        await Promise.all([
            this.page.waitForResponse(r =>r.url().includes('/products')&&r.status()===200),
            this.searchButton.click()
        ]);
        await expect(this.results).not.toContainText(query);
    }

    async emptySearch(){
        await this.searchBox.clear();
        await expect(this.searchBox).toHaveValue('');
        // await Promise.all([
        //     this.page.waitForResponse(r =>r.url().includes('/products')&&r.status()===200),
        //     this.searchButton.click()
        // ]);
        await this.searchButton.click();
        await expect(this.results).toBeVisible();
    }

    }