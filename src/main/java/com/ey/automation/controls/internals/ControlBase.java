package com.ey.automation.controls.internals;

import com.ey.automation.base.DriverContext;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;

import java.util.List;

public class ControlBase implements Control {
    private final WebElement element;
    DriverContext driverContext = new DriverContext();

    public ControlBase(WebElement element) {
        this.element = element;
    }

    public void click() {
        this.driverContext.waitUntilElementClickable(this.element);
        this.element.click();
    }

    public void submit() {
        this.element.submit();
    }

    public void sendKeys(CharSequence... charSequences) {
        this.driverContext.waitUntilElementVisible(this.element);
        this.element.sendKeys(charSequences);
    }

    public void clear() {
        this.driverContext.waitUntilElementVisible(this.element);
        this.element.clear();
    }

    public String getTagName() {
        return this.element.getTagName();
    }

    public String getAttribute(String s) {
        this.driverContext.waitUntilElementVisible(this.element);
        return this.element.getAttribute(s);
    }

    public boolean isSelected() {
        return this.element.isSelected();
    }

    public boolean isEnabled() {
        return this.element.isEnabled();
    }

    public String getText() {
        this.driverContext.waitUntilElementVisible(this.element);
        return this.element.getText();
    }

    public List<WebElement> findElements(By by) {
        return this.element.findElements(by);
    }

    public WebElement findElement(By by) {
        return this.element.findElement(by);
    }

    public boolean isDisplayed() {
        return this.element.isDisplayed();
    }

    public Point getLocation() {
        return this.element.getLocation();
    }

    public Dimension getSize() {
        return this.element.getSize();
    }

    public Rectangle getRect() {
        return this.element.getRect();
    }

    public String getCssValue(String s) {
        return this.element.getCssValue(s);
    }

    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return this.element.getScreenshotAs(outputType);
    }

    public Coordinates getCoordinates() {
        return null;
    }

    public WebElement getWrappedElement() {
        return this.element;
    }

    public ControlBase waitForVisible() {
        this.driverContext.waitUntilElementVisible(this.element);
        return this;
    }

    public ControlBase scrollToElement() {
        this.driverContext.findAndScrollWebElement(this.element);
        return this;
    }

    public void doubleClick() {
        this.driverContext.doubleClickViaActions(this.element);
    }

    public ControlBase waitForInVisible() {
        this.driverContext.waitUntilElementInVisible(this.element);
        return this;
    }

    public ControlBase waitTextVisibleInElement(String text) {
        this.driverContext.waitUntilElementTextVisible(this.element, text);
        return this;
    }

    public ControlBase waitElementClickable() {
        this.driverContext.waitUntilElementClickable(this.element);
        return this;
    }

    public ControlBase waitForVisibleWithExactTime(int timeOutSeconds) {
        this.driverContext.waitUntilElementClickableWithExactTime(this.element, timeOutSeconds);
        return this;
    }
}
