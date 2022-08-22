package com.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class LoginPage extends BasePage {

    @FindBy(id = "ap_email")
    WebElement emailInput;

    @FindBy(css = ".a-button-input")
    WebElement continueButtonInput;

    @FindBy(id = "ap_password")
    WebElement passwordInput;

    @FindBy(id = "auth-signin-button")
    WebElement signInSpan;

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected List<WebElement> elementsToWait() {
        return Arrays.asList(emailInput, continueButtonInput);
    }

    public void setLoginEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void clickContinue() {
        continueButtonInput.click();
    }

    public void setPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickSignIn() {
        signInSpan.click();
    }

    public HomePage signIn(String email, String password) {
        waitToLoad();
        setLoginEmail(email);
        clickContinue();
        setPassword(password);
        clickSignIn();
        return new HomePage(webDriver);
    }
}
