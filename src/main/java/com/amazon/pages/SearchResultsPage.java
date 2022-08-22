package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchResultsPage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(SearchResultsPage.class);

    @FindBy(css = ".s-main-slot.s-result-list.s-search-results")
    WebElement searchResultsList;

    @FindBy(id = "s-refinements")
    WebElement filterOptionsDiv;

    @FindBy(css = "li[aria-label=\"All Prime\"]")
    WebElement primeFilterOption;

    @FindBy(css = "[data-component-type=\"s-search-result\"]")
    List<WebElement> searchResults;


    public SearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected List<WebElement> elementsToWait() {
        return Arrays.asList(searchResultsList, filterOptionsDiv);
    }

    public void clickPrimeOnlyFilter() {
        waitToLoad();
        primeFilterOption.click();
    }

    public boolean isFilterByPrimePresent() {
        List<WebElement> primeFilters = webDriver.findElements(By.cssSelector("li[aria-label=\"All Prime\"]"));
        if (primeFilters.size() == 0) {
            return false;
        } else {
            return true;
        }

    }

    public String getFirstResultText() {
        var text = searchResults.get(0).findElement(By.cssSelector(".a-size-base-plus.a-color-base.a-text-normal")).getText();
        return text;
    }

    public ProductDetailsPage selectFirstSearchResult() {
        searchResults.get(0).click();
        return new ProductDetailsPage(webDriver);
    }

    public boolean checkSearchResultsHavePrimeLogo() {
        List<WebElement> list = new ArrayList<>();
        for (WebElement searchResult : searchResults) {
            try{
            if (searchResult.findElement(By.cssSelector("[aria-label=\"Amazon Prime\"]")).isDisplayed()) {
                list.add(searchResult);
            }
            } catch (NoSuchElementException nse) {
                LOG.info("Handled exception");
            }
        }
        if (list.size() == searchResults.size()) {
            return true;
        } else {
            return false;
        }
    }

}
