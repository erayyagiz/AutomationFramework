package com.ey.automation.base;

import com.ey.automation.controls.api.ControlFactory;

public class Base {

    public static BasePage CurrentPage;

    public <TPage extends BasePage> TPage GetInstance(Class<TPage> page)
    {
        //Object obj = PageFactory.initElements(DriverContext.Driver, page);
        //Custom control page factory initialization
        Object obj = ControlFactory.initElements(DriverContext.Driver, page);
        return page.cast(obj);
    }
}
