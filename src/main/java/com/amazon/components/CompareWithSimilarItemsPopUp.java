package com.amazon.components;

import com.amazon.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CompareWithSimilarItemsPopUp extends BasePage {

    @FindBy(css = ".a-popover-wrapper")
    WebElement compareSimilarItemsPopUpDiv;

    @FindBy(css = ".a-button-close")
    WebElement closePopoverButton;

    @FindBy(css = ".a-row .a-size-base.a-link-normal")
    List<WebElement> productTitles;

    public CompareWithSimilarItemsPopUp(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected List<WebElement> elementsToWait() {
        return Arrays.asList(compareSimilarItemsPopUpDiv, closePopoverButton);
    }

    public void closeCompareWithSimilarItemsPopUp() {
        waitToLoad();
        closePopoverButton.click();
    }

    public boolean similarItemsAreDisplayed(String productName) {
        List<WebElement> list = new ArrayList<>();
        for (WebElement productTitle : productTitles) {
            if (!productTitle.getText().contains(productName)) {
                list.add(productTitle);
            }
        }
        if (list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
