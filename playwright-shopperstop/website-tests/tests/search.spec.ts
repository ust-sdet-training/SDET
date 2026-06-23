import {test} from "../Fixtures/test";


test('Search existing product', async({shop}) => {
    await shop.searchPositiveProduct("Jackets");
});

test('Search non existing product', async({shop}) => {
    await shop.searchNegativeProduct("abcd$%^&");
});

