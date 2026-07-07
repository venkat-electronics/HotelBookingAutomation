package com.hotel.pages;

import com.hotel.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookingPage extends BasePage {
    
    // Personal Details
    @FindBy(id = "firstname")
    private WebElement firstName;
    
    @FindBy(id = "lastname")
    private WebElement lastName;
    
    @FindBy(id = "email")
    private WebElement email;
    
    @FindBy(id = "phone")
    private WebElement phone;
    
    // Room Selection
    @FindBy(css = "[data-testid='room-select']")
    private WebElement roomSelect;
    
    @FindBy(css = ".bui-button--primary")
    private WebElement bookNowButton;
    
    // Special Requests
    @FindBy(css = "[data-testid='special-requests']")
    private WebElement specialRequests;
    
    @FindBy(id = "agreeCheckbox")
    private WebElement agreeCheckbox;
    
    public BookingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public PaymentPage fillBookingDetails(
            String firstName, 
            String lastName, 
            String email, 
            String phone,
            String roomType) {
        
        sendKeys(this.firstName, firstName);
        sendKeys(this.lastName, lastName);
        sendKeys(this.email, email);
        sendKeys(this.phone, phone);
        
        // Select room type
        selectDropdown(roomSelect, roomType);
        
        // Agree to terms
        click(agreeCheckbox);
        
        // Click book now
        click(bookNowButton);
        
        logger.info("Booking details filled for: {} {}", firstName, lastName);
        return new PaymentPage(driver);
    }
}
