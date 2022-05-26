package pages;

import com.ey.automation.base.BasePage;
import com.ey.automation.utilities.ReportListener;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class Page extends BasePage {
    @FindBy(how = How.XPATH, using="//input[@title='Ara']")
    public WebElement searchTxt;
    @FindBy(how = How.XPATH, using="(//input[@class='gNO89b'])[2]")
    public WebElement searchBtn;

    @Step("Write text")
    public void writeText(String text){
        try {
            ReportListener.info("writeText is started!");
            searchTxt.sendKeys(text);
            ReportListener.pass("writeText is completed!");
        } catch (Exception e) {
            ReportListener.fail("writeText is not completed!");
            Assert.fail("writeText is not completed! The reason: "+e.getMessage());
        }
    }

    @Step("Click search button")
    public void clickSearchButton(){
        try {
            ReportListener.info("clickSearchButton is started!");
            searchBtn.click();
            ReportListener.pass("clickSearchButton is completed!");
        } catch (Exception e) {
            ReportListener.fail("clickSearchButton is not completed!");
            Assert.fail("clickSearchButton is not completed! The reason: "+e.getMessage());
        }
    }

}
