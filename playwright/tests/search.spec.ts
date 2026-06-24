import { test } from "../fixtures/nykaFixture";

test.describe('Nykaa Search Tests', () => {

    test('Search valid product name', async ({ nykaa }) => {

        await nykaa.searchValidProduct('SHOAP');

    });

    test('Search invalid product name', async ({ nykaa }) => {

        await nykaa.searchInvalidProduct('adadadads');

    });

});