package com.ey.automation.listener;

import com.ey.automation.base.TestInitialize;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;


public class ReportListener  {
    public ReportListener() {
    }

    public static void info(String details) {
        if (TestInitialize.TEST_ENABLED) {
            Allure.step(details);
        }
    }

    public static void pass(String details) {
        if (TestInitialize.TEST_ENABLED) {
            Allure.step(details,Status.PASSED);
        }
    }


    public static void fail(String details) {
        if (TestInitialize.TEST_ENABLED) {
            Allure.step(details,Status.FAILED);
        }
    }
}
