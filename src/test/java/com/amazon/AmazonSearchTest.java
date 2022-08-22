package com.amazon;

import com.amazon.components.NavBar;
import com.amazon.helpers.FileHelper;
import com.amazon.pages.HomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class AmazonSearchTest extends BaseTest {

    public static final Properties USERNAME_AND_PASSWORD_PROPS =
            FileHelper.getProperties("user-password-config.properties");
    public static final String LOGIN_EMAIL = USERNAME_AND_PASSWORD_PROPS.getProperty("amazon.username");
    public static final String LOGIN_PASSWORD = USERNAME_AND_PASSWORD_PROPS.getProperty("amazon.password");
    public static final String SEARCH_TERM = "Magnifying glass";
    public static final String CATEGORY_NAME = "Industrial & Scientific";
    public static final int QUANTITY = 7;

    private NavBar navBar;
    private HomePage landingPage;

    @BeforeEach
    public void beforeEach() {
        landingPage = new HomePage(webDriver);
        navBar = new NavBar(webDriver);
        landingPage.waitToLoad();
    }

    @Test
    public void searchProduct() {
        landingPage.searchProductInCategory(SEARCH_TERM, CATEGORY_NAME, navBar);
        assertThat(navBar.searchInputFieldContainsSearchTerm(SEARCH_TERM)).as("Search term is populated.").isTrue();
        assertThat(navBar.searchDropdownContainsCategory(CATEGORY_NAME)).as("Category name is selected").isTrue();
    }

    @Test
    public void findFilterByPrimeWithoutLoggingIn() {
        var searchResultsPage = landingPage.searchProductInCategory(SEARCH_TERM, CATEGORY_NAME, navBar);
        assertThat(searchResultsPage.isFilterByPrimePresent()).as("Prime filter is present").isTrue();
    }

    @Test
    public void searchProductAsLoggedInUser() {
        navBar.clickAccountAndListsInNav().signIn(LOGIN_EMAIL, LOGIN_PASSWORD);
        landingPage.searchProductInCategory(SEARCH_TERM, CATEGORY_NAME, navBar);
        assertThat(navBar.isGreetingDisplayed()).as("User logged in.").isTrue();
        assertThat(navBar.searchInputFieldContainsSearchTerm(SEARCH_TERM)).as("Search term is populated.").isTrue();
        assertThat(navBar.searchDropdownContainsCategory(CATEGORY_NAME)).as("Category name is selected").isTrue();
    }

    @Test
    public void searchProductAndFilterByPrime() {
        navBar.clickAccountAndListsInNav().signIn(LOGIN_EMAIL, LOGIN_PASSWORD);
        var searchResultsPage = landingPage.searchProductInCategory(SEARCH_TERM, CATEGORY_NAME, navBar);
        searchResultsPage.clickPrimeOnlyFilter();
        assertThat(searchResultsPage.checkSearchResultsHavePrimeLogo()).as("All search results have prime logo").isTrue();
    }

    @Test
    public void searchProductAndAddToCart() {
        navBar.clickAccountAndListsInNav().signIn(LOGIN_EMAIL, LOGIN_PASSWORD);
        var searchResultsPage = landingPage.searchProductInCategory(SEARCH_TERM, CATEGORY_NAME, navBar);
        searchResultsPage.clickPrimeOnlyFilter();
        var expectedText = searchResultsPage.getFirstResultText();
        var productDetailsPage = searchResultsPage.selectFirstSearchResult();
        assertThat(productDetailsPage.getProductTitle().equals(expectedText)).isTrue();
        productDetailsPage.selectProductQuantity(QUANTITY);
        productDetailsPage.clickAddToCart();
        assertThat(productDetailsPage.isAddedToCartMessageDisplayed()).isTrue();
        var shoppingCartPage = productDetailsPage.viewCart();
        assertThat(shoppingCartPage.getQuantitySelectedValue().equals(String.valueOf(QUANTITY))).isTrue();
    }

    @Test
    public void addAProductToCartAndCompare() {
        navBar.clickAccountAndListsInNav().signIn(LOGIN_EMAIL, LOGIN_PASSWORD);
        var searchResultsPage = landingPage.searchProductInCategory(SEARCH_TERM, CATEGORY_NAME, navBar);
        searchResultsPage.clickPrimeOnlyFilter();
        var productDetailsPage = searchResultsPage.selectFirstSearchResult();
        productDetailsPage.selectProductQuantity(QUANTITY);
        productDetailsPage.clickAddToCart();
        assertThat(productDetailsPage.isAddedToCartMessageDisplayed()).isTrue();
        var shoppingCartPage = productDetailsPage.viewCart();
        var compareSimilarItemsPopUp = shoppingCartPage.clickCompareSimilarItems();
        assertThat(compareSimilarItemsPopUp.similarItemsAreDisplayed(SEARCH_TERM)).isTrue();
        compareSimilarItemsPopUp.closeCompareWithSimilarItemsPopUp();
    }
}
