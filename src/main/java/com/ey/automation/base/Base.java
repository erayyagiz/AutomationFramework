package com.ey.automation.base;

import com.ey.automation.controls.api.ControlFactory;

public class Base extends DriverContext {
    public Base() {
    }

    public <TPage extends BasePage> TPage GetInstance(Class<TPage> page) {
        Object obj = ControlFactory.initElements(LocalDriverContext.getDriver(), page);
        return (TPage) page.cast(obj);
    }
}
