package com.ey.automation.base;

import com.ey.automation.config.ConfigReader;
import com.ey.automation.config.Settings;
import com.ey.automation.utilities.LogUtil;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

public class TestInitialize extends FrameworkInitialize {

    @BeforeTest
    public void Initialize() throws IOException {
        //Initialize Config
        ConfigReader.PopulateSettings();
        //Logging
        Settings.Logs = new LogUtil();
        Settings.Logs.CreateLogFile();
        Settings.Logs.Write("Framework Initialize");
        Settings.Logs.Write("Test Cycle Created");
        InitializeBrowser(Settings.BrowserType);
        Settings.Logs.Write("Browser Initialized");

    }
}
