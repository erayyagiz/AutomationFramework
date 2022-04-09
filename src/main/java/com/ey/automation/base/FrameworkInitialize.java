package com.ey.automation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import java.io.IOException;


public class FrameworkInitialize {

    public FrameworkInitialize() {
    }

    public static WebDriver InitializeBrowser (BrowserType browserType) throws IOException {
        WebDriver driver = null;
        //Properties properties = readBrowserConfig();
        //BrowserType browserType = BrowserType.valueOf(properties.getProperty("BrowserType"));
        if (driver == null) {
            switch (browserType) {
                case Chrome:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions capability = new ChromeOptions();
                    capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    capability.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                    driver = new ChromeDriver(capability);
                    break;
                case Safari:
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support Safari");
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driver = new SafariDriver();
                    break;
                case IE:
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Internet Explorer");
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case Firefox:
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
            }
        }
        LocalDriverContext.setDriver(driver);
        return driver;
    }
}