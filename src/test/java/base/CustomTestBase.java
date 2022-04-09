package base;

import com.ey.automation.base.Base;
import com.ey.automation.base.CurrentPageContext;
import com.ey.automation.base.TestInitialize;
import pages.*;

public class CustomTestBase extends TestInitialize {

    public Page page(){
        Base base = new Base();
        CurrentPageContext.setCurrentPage(base.GetInstance(Page.class));
        Page page = CurrentPageContext.getCurrentPage().As(Page.class);
        return page;
    }
}
