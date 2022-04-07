package com.ey.automation.base;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class DriverContext {
    final Logger driverContextLogger = LogManager.getLogger(DriverContext.class);
    long timeOutInSeconds = 90L;
    String parentWindowId = null;
    ArrayList<String> windowList = new ArrayList();
    public static WebDriver Driver;
    public static Browser Browser;


    public static void setDriver(WebDriver driver) {
        Driver = driver;
    }

    public void navigateToUrl(String url) {
        Driver.get(url);
        this.driverContextLogger.info("Driver Navigated to URL: " + url);
    }

    public void waitForPageToLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) Driver;
            ExpectedCondition jsLoad = (webDriver) -> {
                return jsExecutor.executeScript("return document.readyState", new Object[0]).toString().equals("complete");
            };

            String jsReadyState;
            do {
                wait.until(jsLoad);
                jsReadyState = jsExecutor.executeScript("return document.readyState", new Object[0]).toString();
            } while (!jsReadyState.equalsIgnoreCase("complete"));

            this.driverContextLogger.info("Sayfa icindeki Java Scriptler basarili sekilde yuklenmistir..");
            ExpectedCondition jQueryLoad = (webDriver) -> {
                return (Long) jsExecutor.executeScript("return jQuery.active", new Object[0]) == 0L;
            };

            String jQueryCount;
            do {
                wait.until(jQueryLoad);
                jQueryCount = jsExecutor.executeScript("return jQuery.active", new Object[0]).toString();
            } while (Integer.parseInt(jQueryCount) != 0);

            this.driverContextLogger.info("Sayfa icindeki JQuery'ler basarili sekilde yuklenmistir..");
        } catch (Throwable var6) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var6));
            Assert.fail("DriverContext waitForPageToLoad methodunda HATA olustu !!");
        }

    }

    public void waitUntilElementVisible(WebElement elementFindBy) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOf(elementFindBy));
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Element: " + elementFindBy + " WebElement gorunur degil !!");
        }

    }

    public void waitUntilElementTextVisible(WebElement elementFindBy, String text) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.textToBePresentInElement(elementFindBy, text));
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("Element: " + elementFindBy + " WebElement Text gorunur degil !! Beklenen Text: " + text);
        }

    }

    public boolean waitUntilTextDisplayedEqualsIgnoreCaseByLocator(By locator, String text) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return (Boolean) wait.until(this.checkLocatorTextEqualsIgnoreCase(locator, text));
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("Locator: " + locator + " Beklenen Text: " + text + " esitligi dogrulanamadi !!");
            return false;
        }
    }

    public boolean waitUntilTextDisplayedContainsByLocator(By locator, String text) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return (Boolean) wait.until(this.checkLocatorTextContains(locator, text));
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("Locator: " + locator + " Beklenen Text: " + text + " icermemektedir !!");
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
            return Driver.findElement(locator).isDisplayed();
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
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until((webDriver) -> {
                return webDriver.findElement(locator).isEnabled();
            });
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Locator: " + locator + " WebElement Etkin degil !!");
        }

    }

    public void waitUntilPresenceOfElementByLocator(By locator) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Locator: " + locator + " WebElement HTML kodların (DOM) icerisinde bulunamadi !!");
        }

    }

    public void waitUntilElementClickable(WebElement elementFindBy) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(elementFindBy));
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Element: " + elementFindBy + " WebElement click edilebilir durumda degil !!");
        }

    }

    public boolean waitUntilUrlContains(String expectedUrl) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return (Boolean) wait.until(ExpectedConditions.urlContains(expectedUrl));
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Beklenen URL: " + expectedUrl + " Driver URL: " + Driver.getCurrentUrl() + "!!");
            return false;
        }
    }

    public void waitUntilFrameToBeAvailableAndSwitchToItByName(String frameName) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Beklenen frame isime gore: " + frameName + " bulunamadi !!");
        }

    }

    public void waitUntilFrameToBeAvailableAndSwitchToItByIndex(int frameIndex) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Beklenen frame indexe gore: " + frameIndex + " bulunamadi !!");
        }

    }

    public void waitUntilElementClickableWithExactTime(WebElement element, int exactTimeInSecond) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds((long) exactTimeInSecond))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds((long) exactTimeInSecond)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("Element: " + element + " Verilen Sure: " + exactTimeInSecond + " zarfinda clickable durumda degil !!");
        }

    }

    public void waitUntilVisibleElementWithExactTime(WebElement element, int exactTimeInSecond) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds((long) exactTimeInSecond))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds((long) exactTimeInSecond)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("Element: " + element + " Verilen Sure: " + exactTimeInSecond + " zarfinda gorunur durumda degil !!");
        }

    }

    public boolean waitUntilElementAttributeContainsValue(WebElement element, String attribute, String expectedValue) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            return (Boolean) wait.until(ExpectedConditions.attributeContains(element, attribute, expectedValue));
        } catch (Throwable var5) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var5));
            Assert.fail("Beklenen Değer: " + expectedValue + " Element: " + element + "Attribute: " + attribute + " dogrulunamadi !!");
            return false;
        }
    }

    public void waitUntilElementInVisible(WebElement elementFindBy) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.invisibilityOf(elementFindBy));
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Element: " + elementFindBy + " WebElement gorunur durumdadir!!");
        }

    }

    public void waitUntilSelectOptionsPopulated(Select select) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until((input) -> {
                List<WebElement> options = select.getOptions();
                return options.size() >= 1 ? select : null;
            });
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Select Elementinin icinde secenek bulunmamaktadir !!");
        }

    }

    public void switchTabByGivenTitle(String title) {
        try {
            Set<String> allTabs = Driver.getWindowHandles();
            Iterator var3 = allTabs.iterator();

            while (var3.hasNext()) {
                String eachTab = (String) var3.next();
                Driver.switchTo().window(eachTab);
                if (Driver.getTitle().contains(title)) {
                    break;
                }
            }

            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.titleContains(title));
        } catch (Throwable var5) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var5));
            Assert.fail("Acilan Tablar arasinda beklenen baslik: " + title + " bulunamamistir !!");
        }

    }

    public void getLatestWindow() {
        try {
            this.parentWindowId = Driver.getWindowHandle();
            this.driverContextLogger.info("Parent Window Id: " + this.parentWindowId);
            Set<String> handles = Driver.getWindowHandles();
            Iterator var2 = handles.iterator();

            while (var2.hasNext()) {
                String window = (String) var2.next();
                this.driverContextLogger.info("Window : " + window);
                this.windowList.add(this.windowList.size(), window);
            }

            Driver.switchTo().window((String) this.windowList.get(this.windowList.size() - 1));
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("Yeni sayfaya veya window'a gecerken HATA olustu !!");
        }

    }

    public void returnParentWindow() {
        try {
            Driver.switchTo().window(this.parentWindowId);
        } catch (Throwable var2) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var2));
            Assert.fail("Ana sayfaya veya window'a gecerken HATA olustu !!");
        }

    }

    public boolean checkUrlIsOpened(String url) {
        try {
            Iterator tabIterator = Driver.getWindowHandles().iterator();

            do {
                if (!tabIterator.hasNext()) {
                    return false;
                }

                String eachTab = (String) tabIterator.next();
                Driver.switchTo().window(eachTab);
            } while (!Driver.getCurrentUrl().trim().equalsIgnoreCase(url.trim()));

            return true;
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("URL: " + url + " belirtilen link acilirken HATA olustu !!");
            return false;
        }
    }

    public void findAndScrollWebElement(WebElement element) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) Driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", new Object[]{element});
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Acilan sayfada element: " + element + " scroll edilmesine ragmen bulunamamistir !!");
        }

    }

    public void scrollInWindowByPixels(int x, int y) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) Driver;
            jsExecutor.executeScript("window.scrollBy(" + x + "," + y + ")", new Object[0]);
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("Acilan sayfada X ve Y ekseninde scroll edilme sirasinda HATA olusmustur!!");
        }

    }

    public void dragAndDropWebElement(WebElement from, WebElement to) {
        try {
            Actions action = new Actions(Driver);
            action.dragAndDrop(from, to).build().perform();
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("From Element: " + from + " To Element: " + to + " Elementler drag and drop edilememistir !!");
        }

    }

    public void doubleClickViaActions(WebElement element) {
        try {
            Actions builder = new Actions(Driver);
            builder.moveToElement(element).doubleClick().build().perform();
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Element: " + element + " double click edilememistir !!");
        }

    }

    public List<WebElement> getListOfElement(By locator, int timeOutInSeconds) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(Driver, Duration.ofSeconds((long) timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds((long) timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            List<WebElement> webElementList = (List) wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            return webElementList;
        } catch (Throwable var5) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var5));
            Assert.fail("Element: " + locator + " List olarak get edilemedi!!");
            return null;
        }
    }

    public WebElement getWebElementGivenLocator(By locator) {
        WebElement element = null;

        try {
            element = Driver.findElement(locator);
        } catch (Throwable var4) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var4));
            Assert.fail("Element bulunamadı edilememistir !!");
        }

        return element;
    }

    public boolean isElementPresent(By by, int timeOutInSeconds) {
        FluentWait wait = (new FluentWait(Driver)).withTimeout(Duration.ofSeconds((long) timeOutInSeconds)).pollingEvery(Duration.ofMillis(500L)).ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);

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
            Actions builder = new Actions(Driver);
            builder.moveToElement(element).click().build().perform();
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Element: " + element + " click edilememistir !!");
        }

    }

    public void clickWebElementJS(WebElement element) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) Driver;
            executor.executeScript("arguments[0].click();", new Object[]{element});
        } catch (Throwable var3) {
            this.driverContextLogger.error(ExceptionUtils.getMessage(var3));
            Assert.fail("Element: " + element + " click edilememistir !!");
        }

    }
}
