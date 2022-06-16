package com.ey.automation.base;

public class BasePage extends Base {
    public BasePage() {
    }

    public <TPage> TPage As(Class<TPage> pageInstance) {
        try {
            return (TPage) this;
        } catch (Exception var3) {
            var3.getStackTrace();
            return null;
        }
    }
}
