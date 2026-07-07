package com.hotel.pages;

import com.hotel.base.BasePage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HotelDetailsPage extends BasePage {
    
    @FindBy(css = "[data-testid='hotel-name']")
    private WebElement hotelName;
    
    @FindBy(css = "[data-testid='hotel-address']")
    private WebElement hotelAddress;
    
    @FindBy(css = "[data-testid='description']")
    private WebElement hotelDescription;
    
    @FindBy(css = "[data-testid='hotel-rating']")
    private WebElement hotelRating;
    
    @FindBy(css = "[data-testid='price']")
    private WebElement pricePerNight;
    
    @FindBy(css = ".bui-button--primary")
    private WebElement bookButton;
    
    @FindBy(css = "[data-testid='available-rooms']")
    private List<WebElement> availableRooms;
    
    @FindBy(css = "[data-testid='photos']")
    private List<WebElement> hotelPhotos;
    
    @FindBy(css = ".bui-review-score__text")
    private WebElement reviewScore;
    
    @FindBy(css = ".bui-review-score__badge")
    private WebElement reviewBadge;
    
    public HotelDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public String getHotelName() {
        return getText(hotelName);
    }
    
    public String getHotelAddress() {
        return getText(hotelAddress);
    }
    
    public String getHotelDescription() {
        return getText(hotelDescription);
    }
    
    public String getPricePerNight() {
        return getText(pricePerNight);
    }
    
    public String getHotelRating() {
        return getText(hotelRating);
    }
    
    public BookingPage clickBookNow() {
        click(bookButton);
        logger.info("Clicked book now button");
        return new BookingPage(driver);
    }
    
    public boolean isHotelDetailsDisplayed() {
        return isElementDisplayed(hotelName);
    }
    
    
}
