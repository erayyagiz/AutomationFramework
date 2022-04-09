package pages;

import com.ey.automation.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class Page extends BasePage {
    @FindBy(how = How.XPATH, using="//input[@title='Ara']")
    public WebElement searchTxt;

    public void writeText(){
        searchTxt.sendKeys("HELLO GOOGLE!!");
    }

}
