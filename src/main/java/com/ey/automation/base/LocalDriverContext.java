package com.ey.automation.base;

import org.openqa.selenium.WebDriver;

public class LocalDriverContext {


    public static WebDriver Driver;


    public static void setDriver(WebDriver driver) {
        Driver = driver;
    }

    public static WebDriver getDriver() {
        return Driver;
    }
}
