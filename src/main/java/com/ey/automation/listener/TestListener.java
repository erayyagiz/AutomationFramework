package com.ey.automation.listener;

import com.ey.automation.base.LocalDriverContext;
import com.ey.automation.base.TestInitialize;
import io.qameta.allure.Attachment;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends TestInitialize implements ITestListener {
    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG() {
        return ((TakesScreenshot) LocalDriverContext.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info(getTestMethodName(result) + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info(getTestMethodName(result) + " test is succeed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.info(getTestMethodName(result) + " test is failed.");
        if (LocalDriverContext.getDriver() != null) {
            saveScreenshotPNG();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.info(getTestMethodName(result) + " test is skipped.");
    }


    @Override
    public void onStart(ITestContext context) {
        LOGGER.info("I am in onStart method " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("I am in onFinish method " + context.getName());
    }
}
