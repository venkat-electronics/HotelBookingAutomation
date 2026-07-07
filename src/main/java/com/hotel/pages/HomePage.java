package com.hotel.pages;

import com.hotel.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    
    // Search Elements
    @FindBy(id = "ss")
    private WebElement searchInput;
    
    @FindBy(className = "sb-searchbox__button")
    private WebElement searchButton;
    
    // Date Picker
    @FindBy(css = "[data-mode='checkin']")
    private WebElement checkinDate;
    
    @FindBy(css = "[data-mode='checkout']")
    private WebElement checkoutDate;
    
    // Guest Selector
    @FindBy(css = "[data-testid='occupancy-config']")
    private WebElement guestSelector;
    
    @FindBy(css = "[data-testid='occupancy-popup']")
    private WebElement guestPopup;
    
    // Navigation
    @FindBy(linkText = "Sign in")
    private WebElement signInLink;
    
    @FindBy(linkText = "Register")
    private WebElement registerLink;
    
    @FindBy(className = "bui-header__logo")
    private WebElement logo;
    
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public SearchResultsPage searchHotel(String city, String checkin, String checkout) {
        // Enter city name
        sendKeys(searchInput, city);
        waitUtil.sleep(1000);
        
        // Select dates
        selectDate(checkin, checkout);
        
        // Click search
        click(searchButton);
        logger.info("Searched for hotel in: {}", city);
        
        return new SearchResultsPage(driver);
    }
    
    private void selectDate(String checkin, String checkout) {
        // Implementation for date selection
        // This is simplified - actual booking.com has complex date picker
        logger.info("Selected dates: {} to {}", checkin, checkout);
    }
    
   
    
    public boolean isHomePageDisplayed() {
        return isElementDisplayed(logo);
    }
}
