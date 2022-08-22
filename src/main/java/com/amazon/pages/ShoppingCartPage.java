package com.amazon.pages;

import com.amazon.components.CompareWithSimilarItemsPopUp;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;

public class ShoppingCartPage extends BasePage {

    @FindBy(id = "quantity")
    WebElement quantitySelect;

    @FindBy(css = "input[value=\"Delete\"]")
    WebElement deleteInput;

    @FindBy(css = "input[value=\"Compare with similar items\"]")
    WebElement compareSimilarItemsInput;


    public ShoppingCartPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected List<WebElement> elementsToWait() {
        return Arrays.asList(quantitySelect, deleteInput, compareSimilarItemsInput);
    }

    public void clickDelete() {
        deleteInput.click();
    }

    public CompareWithSimilarItemsPopUp clickCompareSimilarItems() {
        compareSimilarItemsInput.click();
        return new CompareWithSimilarItemsPopUp(webDriver);
    }

    public String getQuantitySelectedValue() {
        Select s = new Select(quantitySelect);
        return s.getFirstSelectedOption().getText();
    }
}
