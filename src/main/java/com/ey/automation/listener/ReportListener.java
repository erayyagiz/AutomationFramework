package com.ey.automation.listener;

import com.ey.automation.base.TestInitialize;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class ReportListener  {
    private static final Logger LOGGER = LogManager.getLogger(ReportListener.class);
    public ReportListener() {
    }

    public static void info(String details) {
        if (TestInitialize.TEST_ENABLED) {
            LOGGER.info(details);
            Allure.step(details);
        }
    }

    public static void pass(String details) {
        if (TestInitialize.TEST_ENABLED) {
            LOGGER.info(details);
            Allure.step(details,Status.PASSED);
        }
    }


    public static void fail(String details) {
        if (TestInitialize.TEST_ENABLED) {
            LOGGER.error(details);
            Allure.step(details,Status.FAILED);
        }
    }
}
