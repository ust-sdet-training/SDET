package com.shopkart.stepdefs;

import com.shopkart.api.ProductClient;
import com.shopkart.data.secrets.Secrets;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ApiProductSearchTest {

    ProductClient client = new ProductClient();

    @Step("Search Product")
    public String searchProduct(String product) {
        return client.getProducts(product)
                .then()
                .extract()
                .path("[0].sku");
    }

    @Step("Validate the Product")
    public void validateTheProduct(String product){
        String sku = searchProduct(product);

        client.getProduct(sku)
                .then()
                .statusCode(200);
    }

    @Step
    public void full_product_search(){
        searchProduct(Secrets.get("product"));
        validateTheProduct(Secrets.get("product"));
    }

}
