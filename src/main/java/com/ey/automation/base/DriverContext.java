package com.ey.automation.base;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class DriverContext {
    long timeOutInSeconds = 90L;

    public DriverContext() {
    }

    public void waitUntilElementVisible(WebElement elementFindBy) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOf(elementFindBy));
        } catch (Throwable var3) {
            Assert.fail("Element: " + elementFindBy + " WebElement gorunur degil !!");
        }

    }
}
