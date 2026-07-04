import { expect, type Page} from "@playwright/test"


export class SearchPage{
    constructor(private readonly page: Page) {}

    private box = () => this.page.getByRole('searchbox');
    resultCount = () => this.page.getByTestId('catalog-result-count');
    cardCount = () => this.page.getByTestId('product-card');

    private searchBtn = () => this.page.getByRole('button', {name: 'Search'});
    
    cartCount = () => this.page.getByTestId('cart-count');

    async goto(){
        await this.page.goto("/catalog")
    }

    async search(q: string){
        const res = this.page.waitForResponse(
            (r) => r.url().includes('/products') && r.status() === 200
        );
    
        await this.box().fill(q);
        await this.searchBtn().click()
        await res;
    }

}