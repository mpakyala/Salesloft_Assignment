package com.amazon.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ProductDetailsPage extends BasePage {

    @FindBy(id = "productTitle")
    WebElement productTitleSpan;

    @FindBy(id = "quantity")
    WebElement quantitySelectSpan;

    @FindBy(id = "add-to-cart-button")
    WebElement addToCartButtonInput;

    @FindBy(id = "buy-now-button")
    WebElement buyNowButtonInput;

    @FindBy(css = "div#attach-added-to-cart-alert-and-image-area span")
    WebElement addedToCartMessageSpanInSideSheet;

    @FindBy(id = "attach-sidesheet-view-cart-button")
    WebElement cartButtonSpan;

    @FindBy(id = "sw-gtc")
    WebElement goToCartSpan;

    public ProductDetailsPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected List<WebElement> elementsToWait() {
        return Arrays.asList(productTitleSpan, quantitySelectSpan, addToCartButtonInput, buyNowButtonInput);
    }

    public String getProductTitle() {
        waitToLoad();
        return productTitleSpan.getText();
    }

    public void selectProductQuantity(int value) {
        Select qty = new Select(quantitySelectSpan);
        if (checkProductAvailability(value)) {
            qty.selectByValue(String.valueOf(value));
        } else throw new IllegalStateException("Low product stock!");
    }

    public void clickAddToCart() {
        addToCartButtonInput.click();
    }

    public boolean isAddedToCartMessageDisplayed() {
        new WebDriverWait(webDriver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(cartButtonSpan));
        return addedToCartMessageSpanInSideSheet.getText().equals("Added to Cart");
    }

    public ShoppingCartPage viewCart() {
        try {
            cartButtonSpan.click();
        } catch (NoSuchElementException nse) {
            goToCartSpan.click();
        }
        return new ShoppingCartPage(webDriver);
    }

    private boolean checkProductAvailability(int value) {
        Select qty = new Select(quantitySelectSpan);
        List<WebElement> quantityList = qty.getOptions();
        if (quantityList.size() > value) {
            return true;
        } else {
            return false;
        }
    }
}
