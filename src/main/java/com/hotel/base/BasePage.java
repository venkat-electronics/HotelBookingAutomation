package com.hotel.base;

import com.hotel.utils.WaitUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BasePage {
    
    protected WebDriver driver;
    protected WaitUtil waitUtil;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;
    protected Logger logger;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtil = new WaitUtil(driver);
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
        this.logger = LogManager.getLogger(this.getClass());
        PageFactory.initElements(driver, this);
    }
    
    protected void click(WebElement element) {
        try {
            waitUtil.waitForElementToBeClickable(element);
            element.click();
            logger.info("Clicked on element");
        } catch (Exception e) {
            jsExecutor.executeScript("arguments[0].click();", element);
            logger.info("Clicked using JavaScript");
        }
    }
    
    protected void sendKeys(WebElement element, String text) {
        waitUtil.waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
        logger.info("Entered text: {}", text);
    }
    
    protected String getText(WebElement element) {
        waitUtil.waitForElementVisible(element);
        return element.getText().trim();
    }
    
    protected void selectDropdown(WebElement element, String value) {
        waitUtil.waitForElementVisible(element);
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(value);
        logger.info("Selected dropdown value: {}", value);
    }
    
    protected void hoverOver(WebElement element) {
        actions.moveToElement(element).build().perform();
        logger.info("Hovered over element");
    }
    
    protected void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled to element");
    }
    
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return waitUtil.waitForElementToBeVisible(element);
        } catch (Exception e) {
            return false;
        }
    }
}
