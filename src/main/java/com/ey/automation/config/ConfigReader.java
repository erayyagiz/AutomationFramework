package com.ey.automation.config;

import com.ey.automation.base.BrowserType;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    public static Properties readBrowserConfig() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream("src/main/java/resources/config.properties");
        properties.load(inputStream);
        Settings.BrowserType = BrowserType.valueOf(properties.getProperty("BrowserType"));
        Settings.BaseURL = properties.getProperty("BaseURL");
        return properties;
    }
}
