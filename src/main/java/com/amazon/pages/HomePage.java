package com.amazon.pages;

import com.amazon.components.NavBar;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class HomePage extends BasePage {

    @FindBy(id = "navbar")
    WebElement navBarDiv;
    @FindBy(id = "desktop-banner")
    WebElement bannerDiv;


    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected List<WebElement> elementsToWait() {
        return Arrays.asList(navBarDiv, bannerDiv);
    }

    public SearchResultsPage searchProductInCategory(String product, String category, NavBar navBar) {
        navBar.waitToLoad();
        navBar.setSearchInput(product);
        navBar.selectCategory(category);
        navBar.clickSearch();
        return new SearchResultsPage(webDriver);
    }
}

