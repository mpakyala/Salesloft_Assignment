package com.amazon.components;

import com.amazon.pages.BasePage;
import com.amazon.pages.LoginPage;
import com.amazon.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;

public final class NavBar extends BasePage {

    @FindBy(id = "twotabsearchtextbox")
    WebElement searchInput;

    @FindBy(id = "searchDropdownBox")
    WebElement searchDropdownSelect;

    @FindBy(id = "nav-search-submit-button")
    WebElement searchButton;

    @FindBy(id = "nav-link-accountList-nav-line-1")
    WebElement navAccountAndListsSpan;

    @FindBy(css = "a#nav-item-signout")
    WebElement signOutAnchor;

    @FindBy(id = "nav-cart-count")
    WebElement cartCountDiv;

    @FindBy(id = "nav-cart-count-container")
    WebElement navCartDiv;

    public NavBar(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected List<WebElement> elementsToWait() {
        return Arrays.asList(searchInput, searchButton);
    }

    public void setSearchInput(String productName) {
        searchInput.sendKeys(productName);
    }

    public void clickSearch() {
        searchButton.click();
    }

    public void selectCategory(String category) {
        new Select(searchDropdownSelect).selectByVisibleText(category);
    }

    public boolean searchInputFieldContainsSearchTerm(String searchTerm) {
        return searchInput.getAttribute("value").equals(searchTerm);
    }

    public boolean searchDropdownContainsCategory(String expectedCategory) {
        return new Select(searchDropdownSelect).getFirstSelectedOption().getText().equals(expectedCategory);
    }

    public LoginPage clickAccountAndListsInNav() {
        navAccountAndListsSpan.click();
        return new LoginPage(webDriver);
    }

    public boolean isGreetingDisplayed() {
        return navAccountAndListsSpan.getText().equals("Hello, Manasa");
    }

    public void signOut() {
        hover(navAccountAndListsSpan);
        signOutAnchor.click();
    }

    public String getCartCount() {
        return cartCountDiv.getText();
    }

    public ShoppingCartPage clickCart() {
        navCartDiv.click();
        return new ShoppingCartPage(webDriver);
    }
}
