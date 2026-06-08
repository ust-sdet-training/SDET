import { expect , test} from "@playwright/test";
import { SearchPage } from "../pages/SearchPage";

test("Search by product ", async ({page})=>{
    const searchProduct = new SearchPage(page);
    await searchProduct.searchToCart();
});