package com.amazon;

import com.amazon.components.NavBar;
import com.amazon.config.FrameworkConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

public class BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);
    private static final FrameworkConfig FRAMEWORK_CONFIG = ConfigFactory.create(FrameworkConfig.class);
    private static final String URL = constructUrl(FRAMEWORK_CONFIG);
    WebDriver webDriver;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        LOG.info("Starting test [{}].", testInfo.getTestMethod().get().getName());
        webDriver = new ChromeDriver();

        turnOffSeleniumRemoteLogging();
        webDriver.get(URL);
        webDriver.manage().window().maximize();
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        LOG.info("Finishing test [{}]", testInfo.getTestMethod().get().getName());
        clearCart();
        webDriver.close();
        webDriver.quit();
    }

    public void turnOffSeleniumRemoteLogging() {
        java.util.logging.Logger.getLogger("org.openqa.selenium.remote")
                .setLevel(Level.OFF);
    }

    public void clearCart() {
        var navBar = new NavBar(webDriver);
        if (!navBar.getCartCount().equals("0")) {
            navBar.clickCart().clickDelete();
        }
    }

    public static String constructUrl(FrameworkConfig frameworkConfig) {
        var env = frameworkConfig.environment();
        var hostname = frameworkConfig.hostname();
        StringBuilder sb = new StringBuilder();
        if (!env.equals("prod")) {
            sb.append("https://").append(env).append(".").append(hostname);
        } else {
            sb.append("https://www.").append(hostname);
        }
        return sb.toString();

    }
}
