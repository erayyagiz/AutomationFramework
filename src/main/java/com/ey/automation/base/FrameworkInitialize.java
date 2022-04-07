package com.ey.automation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;

public class FrameworkInitialize extends Base {

    public static void InitializeBrowser(BrowserType browserType) {
        WebDriver driver = null;
        switch (browserType) {
            case Chrome: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions capability = new ChromeOptions();
                capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capability.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                driver = new ChromeDriver(capability);
                driver.manage().window().maximize();
                break;
            }
            case Firefox: {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            }
            case IE: {
                if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                    throw new WebDriverException("Your OS doesn't support Internet Explorer");
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            }
        }

        //Set the Driver
        DriverContext.setDriver(driver);
        //Browser
        DriverContext.Browser = new Browser(driver);
    }
}