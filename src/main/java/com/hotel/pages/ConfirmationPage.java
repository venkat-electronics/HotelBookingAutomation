package com.hotel.pages;

import com.hotel.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage extends BasePage {
    
    @FindBy(css = ".booking-confirmation")
    private WebElement confirmationMessage;
    
    @FindBy(css = ".booking-number")
    private WebElement bookingNumber;
    
    @FindBy(css = ".booking-details")
    private WebElement bookingDetails;
    
    @FindBy(css = ".btn-print")
    private WebElement printButton;
    
    @FindBy(css = ".btn-download")
    private WebElement downloadButton;
    
    @FindBy(linkText = "View My Bookings")
    private WebElement viewBookingsLink;
    
    public ConfirmationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public String getConfirmationMessage() {
        return getText(confirmationMessage);
    }
    
    public String getBookingNumber() {
        return getText(bookingNumber);
    }
    
    public String getBookingDetails() {
        return getText(bookingDetails);
    }
    
    public boolean isBookingConfirmed() {
        return isElementDisplayed(confirmationMessage);
    }
    
    public void printConfirmation() {
        click(printButton);
        logger.info("Printed confirmation");
    }
    
    public void downloadConfirmation() {
        click(downloadButton);
        logger.info("Downloaded confirmation");
    }
}
