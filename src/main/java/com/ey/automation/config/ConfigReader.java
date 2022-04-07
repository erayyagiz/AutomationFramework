package com.ey.automation.config;

import com.ey.automation.base.BrowserType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    public static  void PopulateSettings() throws IOException {
        ConfigReader reader = new ConfigReader();
        reader.ReadProperty();
    }

    private void ReadProperty() throws IOException {
        //Create Property Object
        Properties p = new Properties();
        //Load the Property file available in same package
        InputStream inputStream = new FileInputStream("src/main/java/resources/config.properties");
        p.load(inputStream);
        //Browser Type
        Settings.BrowserType = BrowserType.valueOf(p.getProperty("BrowserType"));
    }
}
