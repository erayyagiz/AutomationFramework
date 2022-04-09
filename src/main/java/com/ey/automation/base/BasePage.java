package com.ey.automation.base;

public abstract class BasePage extends Base {
    public BasePage() {
    }

    public <TPage extends BasePage> TPage As(Class<TPage> pageInstance) {
        try {
            return (TPage) this;
        } catch (Exception var3) {
            var3.getStackTrace();
            return null;
        }
    }
}
