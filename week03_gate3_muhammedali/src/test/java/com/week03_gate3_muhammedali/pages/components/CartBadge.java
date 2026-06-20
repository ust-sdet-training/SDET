package com.week03_gate3_muhammedali.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.primitives.Bytes;

public class CartBadge  {
    private static final By COUNT = By.cssSelector("[data-test='cart-count']");

    private final WebDriverWait wait;

    public CartBadge(WebDriverWait wait){
        this.wait = wait;
    }

    public int count(){
        return Integer.parseInt( wait.until(ExpectedConditions.visibilityOfElementLocated(COUNT)).getText());
    }

    public byte[] getcountByte(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(COUNT)).getText().getBytes();
    }

    public void expectCount(int expectedCount){
        wait.until(ExpectedConditions.textToBe(COUNT, String.valueOf(expectedCount)));
    }

}
