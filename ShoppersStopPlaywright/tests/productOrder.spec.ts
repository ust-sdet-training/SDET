import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';
import { ProductListingPage } from '../pages/ProductListingPage';
import { ProductPage } from '../pages/ProductPage';
import { BagPage } from '../pages/BagPage';
import {product} from '../test-data/productData'


test.only('Search and Place Order Test',async({page})=>{

  const homePage = new HomePage(page);
  await homePage.gotoShoppersStop();
  await homePage.searchProduct(product.name);

  const productListingPage = new ProductListingPage(page);
  await productListingPage.filterByBrand();
  await productListingPage.chooseBrand(product.brandName);

  const productPagePromise = page.waitForEvent('popup');
  await productListingPage.selectProduct(product.cardName);
  const newPage = await productPagePromise;

  const productPage = new ProductPage(newPage);
  await productPage.checkPincode(product.pincode);
  await productPage.isPincodeGreen();
  await productPage.clickAddToBag();
  await productPage.clickGoToBag();

});


