package com.ey.automation.base;

public class CurrentPageContext {
    private static final ThreadLocal<BasePage> localCurrentPage = new ThreadLocal();

    public CurrentPageContext() {
    }

    public static BasePage getCurrentPage() {
        return (BasePage)localCurrentPage.get();
    }

    public static void setCurrentPage(BasePage driverThreadLocal) {
        localCurrentPage.set(driverThreadLocal);
    }
}
