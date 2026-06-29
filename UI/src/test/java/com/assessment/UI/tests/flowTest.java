package com.assessment.UI.tests;

import com.assessment.UI.Base.driverFactory;
import com.assessment.UI.pages.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class flowTest {

    private homePage home;
    private searchPage search;
    private productPage product;
    private wishlistPage wishlist;

    @BeforeEach
    void setUp() {

        driverFactory.initializeBrowser();

        home = new homePage();
        search = new searchPage();
        product = new productPage();
        wishlist = new wishlistPage();

    }

    @AfterEach
    void tearDown() {

        driverFactory.quitBrowser();

    }

    @Test
    @DisplayName("Verify Wishlist Flow")
    void verifyWishlistFlow() {

        home.searchProduct("Watch");

        search.openFirstProduct();

        String expectedProduct = product.getProductName();

        product.clickWishlist();

        if (product.isLoginPopupDisplayed()) {

            assertTrue(product.isLoginPopupDisplayed());

            return;
        }

        wishlist.openWishlist();

        assertAll(

                () -> assertTrue(
                        wishlist.verifyProduct(expectedProduct)
                ),

                () -> {

                    wishlist.removeProduct();

                    assertTrue(
                            wishlist.isWishlistEmpty()
                    );

                }

        );

    }

}
