import { test } from "../fixtures/test";



test('customer login',async ({ shop }) => {

    await shop.doLogin();
});

test('locked user cannot login',async ({ shop }) => {

    await shop.loginLockedUser();
}
);


test('complete order flow',async({shop})=>{
    await shop.doLogin();
    await shop.searchProduct();
    await shop.configureProduct('30 L','Graphite','1','Store pickup');
    await shop.addCart();
    await shop.checkoutOrder();
    await shop.placeOrder();
});



test('search existing product',async ({ shop }) => {

    await shop.doLogin();
    await shop.searchProduct('travel');
});

test('search nonexistent product',async ({ shop }) => {

    await shop.doLogin();
    await shop.searchNoResults('xyzabc');
});

test('empty search',async ({ shop }) => {

    await shop.doLogin();
    await shop.emptySearch();
});



test('add searched product to cart',async ({ shop }) => {
    await shop.doLogin();
    await shop.searchProduct();
    await shop.configureProduct('30 L','Graphite','1','Store pickup');
    await shop.addCart();
});

test('checkout from cart',async({shop})=>{
    await shop.doLogin();
    await shop.searchProduct();
    await shop.configureProduct('30 L','Graphite','1','Store pickup');
    await shop.addCart();
    await shop.checkoutOrder();
}
);