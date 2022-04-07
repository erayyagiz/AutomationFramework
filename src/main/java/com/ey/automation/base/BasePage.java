package com.ey.automation.base;

public abstract class BasePage extends Base {

   /* public BasePage() {
        PageFactory.initElements(DriverContext.Driver, this);
    }*/

    public <TPage extends BasePage> TPage As(Class<TPage> pageInstance) {
        try {
            return (TPage) this;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
}
