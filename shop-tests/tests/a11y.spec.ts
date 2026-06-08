import { test, expect } from "../fixtures/test";
import AxeBuilder from "@axe-core/playwright";
 
async function checkCritical(page){
const results=await new AxeBuilder({page}).withTags(["wcag2a","wcag2aa"]).analyze();
expect(results.violations.filter(v=>v.impact==="critical")).toEqual([]);
}
 
test('a11y login',async({shop})=>{await shop.login.open();
    await checkCritical(shop.login.page);});
 
test('a11y catalog',async({shop})=>{await shop.doLogin();
    await shop.search.open();
    await checkCritical(shop.search.page);});
 
test('a11y product',async({shop})=>{await shop.doLogin();
    await shop.searchProduct();
    await checkCritical(shop.product.page);});
 
test('a11y cart',async({shop})=>{await shop.doLogin();
    await shop.searchProduct();
    await shop.configureProduct('30 L','Graphite','1','Store pickup');
    await shop.addCart();
    await checkCritical(shop.cart.page);});
 
test('a11y checkout',async({shop})=>{await shop.doLogin();
    await shop.searchProduct();
    await shop.configureProduct('30 L','Graphite','1','Store pickup');
    await shop.addCart();
    await shop.checkoutOrder();
    await checkCritical(shop.checkout.page);});
 
test('a11y place order',async({shop})=>{await shop.doLogin();
    await shop.searchProduct();
    await shop.configureProduct('30 L','Graphite','1','Store pickup');
    await shop.addCart();
    await shop.checkoutOrder();
    await shop.placeOrder();
    await checkCritical(shop.checkout.page);});