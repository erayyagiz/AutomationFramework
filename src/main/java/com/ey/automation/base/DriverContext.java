package com.ey.automation.base;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class DriverContext {
    final Logger driverContextLogger = LogManager.getLogger(DriverContext.class);
    String parentWindowId = null;
    ArrayList<String> windowList = new ArrayList();

    public DriverContext() {
    }

    public void navigateToUrl(String url) {
        LocalDriverContext.getDriver().get(url);
        this.driverContextLogger.info("Driver Navigated to URL: " + url);
    }

    public void waitFor(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (Exception e) {
        }
    }

    public void waitForPageToLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) LocalDriverContext.getDriver();
            ExpectedCondition<Boolean> jsLoad = webDriver -> ((JavascriptExecutor) LocalDriverContext.getDriver())
                    .executeScript("return document.readyState").toString().equals("complete");
            //Get JS Ready
            boolean jsReady = jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            if (!jsReady)
                wait.until(jsLoad);
            else
                this.driverContextLogger.info("The page has been loaded successfully!");
        } catch (Throwable var6) {
            this.driverContextLogger.error("The page can not been loaded!");
            Assert.fail("The page can not been loaded!");
        }
    }

    public String getCookieName(String cookieName) {
        try {
            String cookie = LocalDriverContext.getDriver().manage().getCookieNamed(cookieName).toString();
            String[] cookies = cookie.split(" ");
            cookie = cookies[0];
            Pattern pat = Pattern.compile("(?<="+cookieName+"=)(.*?)(?=;)");
            Matcher m = pat.matcher(cookie);
            if (m.find()) {
                cookie = m.group(0);
            }else {
                this.driverContextLogger.error("The cookie is empty!");
                Assert.fail("The cookie is empty!");
            }
            return cookie;
        } catch (Throwable var6) {
            this.driverContextLogger.error("The cookie can not be taken!");
            Assert.fail("The cookie can not be taken!");
            return null;
        }
    }

    public void waitUntilElementVisible(WebElement elementFindBy) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(elementFindBy));
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + elementFindBy + " cannot be displayed!");
            Assert.fail("The element: " + elementFindBy + " cannot be displayed!");
        }
    }

    public void waitUntilElementTextVisible(WebElement elementFindBy, String text) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.textToBePresentInElement(elementFindBy, text));
        } catch (Throwable var4) {
            this.driverContextLogger.error("The element: " + elementFindBy + " cannot be displayed! The expected text: " + text);
            Assert.fail("The element: " + elementFindBy + " cannot be displayed! The expected text: " + text);
        }

    }

    public boolean waitUntilTextDisplayedEqualsIgnoreCaseByLocator(By locator, String text) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            return (Boolean) wait.until(this.checkLocatorTextEqualsIgnoreCase(locator, text));
        } catch (Throwable var4) {
            this.driverContextLogger.error("The element: " + locator + " The expected text: " + text + " is not equal!");
            Assert.fail("The element: " + locator + " The expected text: " + text + " is not equal!");
            return false;
        }
    }

    public boolean waitUntilTextDisplayedContainsByLocator(By locator, String text) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            return (Boolean) wait.until(this.checkLocatorTextContains(locator, text));
        } catch (Throwable var4) {
            this.driverContextLogger.error("The element: " + locator + " The expected text: " + text + " has not contain!");
            Assert.fail("The element: " + locator + " The expected text: " + text + " has not contain!");
            return false;
        }
    }

    public ExpectedCondition<Boolean> checkLocatorTextEqualsIgnoreCase(By locator, String text) {
        return (webDriver) -> {
            assert webDriver != null;

            return webDriver.findElement(locator).getText().equalsIgnoreCase(text);
        };
    }

    public boolean checkIsDisplayedBylocator(By locator) {
        try {
            return LocalDriverContext.getDriver().findElement(locator).isDisplayed();
        } catch (Throwable var3) {
            return false;
        }
    }

    public boolean checkElementIsDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Throwable var3) {
            return false;
        }
    }

    public ExpectedCondition<Boolean> checkLocatorTextContains(By locator, String text) {
        return (webDriver) -> {
            assert webDriver != null;

            return webDriver.findElement(locator).getText().contains(text);
        };
    }

    public void waitUntilElementEnabled(By locator) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until((webDriver) -> {
                return webDriver.findElement(locator).isEnabled();
            });
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + locator + " is not enabled!");
            Assert.fail("The element: " + locator + " is not enabled!");
        }

    }

    public void waitUntilPresenceOfElementByLocator(By locator) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + locator + " can not be found in DOM!");
            Assert.fail("The element: " + locator + " can not be found in DOM!");
        }

    }

    public void waitUntilElementClickable(WebElement elementFindBy) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(elementFindBy));
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + elementFindBy + " is not clickable!");
            Assert.fail("The element: " + elementFindBy + " is not clickable!");
        }

    }

    public boolean waitUntilUrlContains(String expectedUrl) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            return (Boolean) wait.until(ExpectedConditions.urlContains(expectedUrl));
        } catch (Throwable var3) {
            this.driverContextLogger.error("The expected URL: " + expectedUrl + " The current URL: " + LocalDriverContext.getDriver().getCurrentUrl() + "!");
            Assert.fail("The expected URL: " + expectedUrl + " The current URL: " + LocalDriverContext.getDriver().getCurrentUrl() + "!");
            return false;
        }
    }

    public void waitUntilFrameToBeAvailableAndSwitchToItByWebelement(WebElement webElement) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(webElement));
        } catch (Throwable var3) {
            this.driverContextLogger.error("The frame can not be found according to the expected element: " + webElement + "!");
            Assert.fail("The frame can not be found according to the expected element: " + webElement + "!");
        }

    }

    public void waitUntilFrameToBeAvailableAndSwitchToItByIndex(int frameIndex) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
        } catch (Throwable var3) {
            this.driverContextLogger.error("The frame can not be found according to the expected index: " + frameIndex + "!");
            Assert.fail("The frame can not be found according to the expected index: " + frameIndex + "!");
        }

    }

    public void waitUntilElementClickableWithExactTime(WebElement element, int exactTimeInSecond) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Throwable var4) {
            this.driverContextLogger.error("The element: " + element + " can not be clickable in this time: " + exactTimeInSecond + "!");
            Assert.fail("The element: " + element + " can not be clickable in this time: " + exactTimeInSecond + "!");
        }

    }

    public void waitUntilVisibleElementWithExactTime(WebElement element, int exactTimeInSecond) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Throwable var4) {
            this.driverContextLogger.error("The element: " + element + " is not visible in this time: " + exactTimeInSecond + "!");
            Assert.fail("The element: " + element + " is not visible in this time: " + exactTimeInSecond + "!");
        }

    }

    public boolean waitUntilElementAttributeContainsValue(WebElement element, String attribute, String expectedValue) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            return (Boolean) wait.until(ExpectedConditions.attributeContains(element, attribute, expectedValue));
        } catch (Throwable var5) {
            this.driverContextLogger.error("The expected value: " + expectedValue + " The element: " + element + "The attribute: " + attribute + " can not be validated!");
            Assert.fail("The expected value: " + expectedValue + " The element: " + element + "The attribute: " + attribute + " can not be validated!");
            return false;
        }
    }

    public void waitUntilElementInVisible(WebElement elementFindBy) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.invisibilityOf(elementFindBy));
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + elementFindBy + " is visible!");
            Assert.fail("The element: " + elementFindBy + " is visible!");
        }

    }

    public void waitUntilSelectOptionsPopulated(Select select) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until((input) -> {
                List<WebElement> options = select.getOptions();
                return options.size() >= 1 ? select : null;
            });
        } catch (Throwable var3) {
            this.driverContextLogger.error("There is no any option in select element!");
            Assert.fail("There is no any option in select element!");
        }

    }

    public void switchTabByGivenTitle(String title) {
        try {
            Set<String> allTabs = LocalDriverContext.getDriver().getWindowHandles();
            Iterator var3 = allTabs.iterator();

            while (var3.hasNext()) {
                String eachTab = (String) var3.next();
                LocalDriverContext.getDriver().switchTo().window(eachTab);
                if (LocalDriverContext.getDriver().getTitle().contains(title)) {
                    break;
                }
            }
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.titleContains(title));
        } catch (Throwable var5) {
            this.driverContextLogger.error("The title: " + title + " can not be found in opened tabs!");
            Assert.fail("The title: " + title + " can not be found in opened tabs!");
        }

    }

    public void openNewTabAndNavigateToURL(String url) {
        try {
            LocalDriverContext.getDriver().switchTo().newWindow(WindowType.TAB).navigate().to(url);
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.urlContains(url));
        } catch (Throwable var5) {
            this.driverContextLogger.error("The url: " + url + " can not be found in opened tabs!");
            Assert.fail("The url: " + url + " can not be found in opened tabs!");
        }

    }

    public void getLatestWindow() {
        try {
            this.parentWindowId = LocalDriverContext.getDriver().getWindowHandle();
            this.driverContextLogger.info("Parent Window Id: " + this.parentWindowId);
            Set<String> handles = LocalDriverContext.getDriver().getWindowHandles();
            Iterator var2 = handles.iterator();

            while (var2.hasNext()) {
                String window = (String) var2.next();
                this.driverContextLogger.info("Window : " + window);
                this.windowList.add(this.windowList.size(), window);
            }

            LocalDriverContext.getDriver().switchTo().window((String) this.windowList.get(this.windowList.size() - 1));
        } catch (Throwable var4) {
            this.driverContextLogger.error("The new page can not be switched to!");
            Assert.fail("The new page can not be switched to!");
        }

    }

    public void returnParentWindow() {
        try {
            LocalDriverContext.getDriver().switchTo().window(this.parentWindowId);
        } catch (Throwable var2) {
            this.driverContextLogger.error("The old page can not be switched to!");
            Assert.fail("The old page can not be switched to!");
        }

    }

    public boolean checkUrlIsOpened(String url) {
        try {
            Iterator tabIterator = LocalDriverContext.getDriver().getWindowHandles().iterator();

            do {
                if (!tabIterator.hasNext()) {
                    return false;
                }

                String eachTab = (String) tabIterator.next();
                LocalDriverContext.getDriver().switchTo().window(eachTab);
            } while (!LocalDriverContext.getDriver().getCurrentUrl().trim().equalsIgnoreCase(url.trim()));

            return true;
        } catch (Throwable var4) {
            this.driverContextLogger.error("The URL: " + url + " can not be displayed!");
            Assert.fail("The URL: " + url + " can not be displayed!");
            return false;
        }
    }

    public void findAndScrollWebElement(WebElement element) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) LocalDriverContext.getDriver();
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", new Object[]{element});
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + element + " can not be found!");
            Assert.fail("The element: " + element + " can not be found!");
        }

    }

    public void scrollInWindowByPixels(int x, int y) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) LocalDriverContext.getDriver();
            jsExecutor.executeScript("window.scrollBy(" + x + "," + y + ")", new Object[0]);
        } catch (Throwable var4) {
            this.driverContextLogger.error("The scrolling can not be done!");
            Assert.fail("The scrolling can not be done!");
        }

    }

    public void dragAndDropWebElement(WebElement from, WebElement to) {
        try {
            Actions action = new Actions(LocalDriverContext.getDriver());
            action.dragAndDrop(from, to).build().perform();
        } catch (Throwable var4) {
            this.driverContextLogger.error("From Element: " + from + " To Element: " + to + " can not be dragged and dropped!");
            Assert.fail("From Element: " + from + " To Element: " + to + " can not be dragged and dropped!");
        }

    }

    public void doubleClickViaActions(WebElement element) {
        try {
            Actions builder = new Actions(LocalDriverContext.getDriver());
            builder.moveToElement(element).doubleClick().build().perform();
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + element + " can not be clicked!");
            Assert.fail("The element: " + element + " can not be clicked!");
        }

    }

    public List<WebElement> getListOfElement(By locator, int timeOutInSeconds) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
            List<WebElement> webElementList = (List) wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            return webElementList;
        } catch (Throwable var5) {
            this.driverContextLogger.error("The element: " + locator + " can not be gotten as a list!");
            Assert.fail("The element: " + locator + " can not be gotten as a list!");
            return null;
        }
    }

    public List<WebElement> presenceOfAllWait(By locator, int seconds) {
        try {
            WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(seconds));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception ex) {
            this.driverContextLogger.error("The element: "+locator+" cannot be found! The reason: "+ex.getMessage());
            Assert.fail("The element: "+locator+" cannot be found! The reason: "+ex.getMessage());
            return null;
        }
    }

    public WebElement getWebElementGivenLocator(By locator) {
        WebElement element = null;

        try {
            element = LocalDriverContext.getDriver().findElement(locator);
        } catch (Throwable var4) {
            this.driverContextLogger.error("The locator: "+locator+" can not be found!");
            Assert.fail("The locator: "+locator+" can not be found!");
        }
        return element;
    }

    public boolean isElementPresent(By by, int timeOutInSeconds) {
        WebDriverWait wait= new WebDriverWait(LocalDriverContext.getDriver(), Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (Exception var5) {
            return false;
        }
    }

    public boolean isWebElementVisible(WebElement webElement) {
        if (webElement != null) {
            try {
                return webElement.isDisplayed();
            } catch (NoSuchElementException var3) {
                return false;
            }
        } else {
            return false;
        }
    }

    public void clickViaActions(WebElement element) {
        try {
            Actions builder = new Actions(LocalDriverContext.getDriver());
            builder.moveToElement(element).click().build().perform();
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + element + " can not be clicked!");
            Assert.fail("The element: " + element + " can not be clicked!");
        }

    }

    public void clickWebElementJS(WebElement element) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) LocalDriverContext.getDriver();
            executor.executeScript("arguments[0].click();", new Object[]{element});
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + element + " can not be clicked!");
            Assert.fail("The element: " + element + " can not be clicked!");
        }
    }

    public void clickAndHold(WebElement element) {
        try {
            Actions actions = new Actions(LocalDriverContext.getDriver());
            actions.clickAndHold(element).perform();
        } catch (Throwable var3) {
            this.driverContextLogger.error("The element: " + element + " can not be holded!");
            Assert.fail("The element: " + element + " can not be holded!");
        }
    }

    public String generateRandom(String characters, int length){
        try {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for(int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                char randomChar = characters.charAt(index);
                sb.append(randomChar);
            }
            String randomString = sb.toString();
            return randomString;
        } catch (Exception e) {
            Assert.fail("Random string can not be generated.");
            return null;
        }
    }

    public Integer generateRandomInteger(int min, int max) {
        try {
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            return random_int;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTextbox(WebElement webElement) {
        try {
            webElement.sendKeys(Keys.CONTROL+"a");
            webElement.sendKeys(Keys.DELETE);
        } catch (Exception e) {
            driverContextLogger.error("The text box can not be deleted.");
            Assert.fail("The text box can not be deleted.");
        }
    }
}
