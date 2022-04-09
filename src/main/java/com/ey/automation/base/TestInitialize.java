package com.ey.automation.base;

import com.ey.automation.config.ConfigReader;
import com.ey.automation.config.Settings;
import org.testng.annotations.*;
import java.io.IOException;
import java.time.Duration;

public class TestInitialize {
    public TestInitialize() {
    }

    @BeforeSuite(alwaysRun = true)
    public void Initialize() throws IOException {
        ConfigReader.readBrowserConfig();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() throws IOException {
        LocalDriverContext.setDriver(FrameworkInitialize.InitializeBrowser(Settings.BrowserType));
        LocalDriverContext.getDriver().get(Settings.BaseURL);
        LocalDriverContext.getDriver().manage().window().maximize();
        LocalDriverContext.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofMinutes(2L));
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(){
//        if(scenario.isFailed()){
//            final byte[] screenshot = ((TakesScreenshot) LocalDriverContext.getDriver()).getScreenshotAs(OutputType.BYTES);
//            scenario.attach(screenshot,"image/png", "screenshot");
//        }
        if(LocalDriverContext.getDriver() != null){
            LocalDriverContext.getDriver().quit();
        }
    }
}
