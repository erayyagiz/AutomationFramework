package com.ey.automation.base;

import com.ey.automation.config.ConfigReader;
import com.ey.automation.config.Settings;
import io.qameta.allure.Step;
import org.testng.annotations.*;
import java.io.IOException;
import java.time.Duration;

public class TestInitialize {
    public static boolean TEST_ENABLED = Boolean.FALSE;
    public static String certificatesTrustStorePath = System.getenv("JAVA_JRE")+"\\lib\\security\\cacerts";

    public TestInitialize() {
    }

    @BeforeSuite(alwaysRun = true)
    public void Initialize() throws IOException {
        ConfigReader.readBrowserConfig();
        TEST_ENABLED = Boolean.TRUE;
        System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
    }

    @Step("Open the browser")
    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        LocalDriverContext.setDriver(FrameworkInitialize.InitializeBrowser(Settings.BrowserType));
        LocalDriverContext.getDriver().get(Settings.BaseURL);
        LocalDriverContext.getDriver().manage().window().maximize();
        LocalDriverContext.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        //LocalDriverContext.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofMinutes(2L));
    }

    @Step("Close the browser")
    @AfterMethod(alwaysRun = true)
    public void afterTest(){
        if(LocalDriverContext.getDriver() != null){
            LocalDriverContext.getDriver().quit();
        }
    }
}
