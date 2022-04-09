package com.ey.automation.base;

import org.openqa.selenium.WebDriver;

public class LocalDriverContext {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal();

    public LocalDriverContext() {
    }

    public static void setDriver(WebDriver driver) {
        driverThread.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThread.get();
    }
}