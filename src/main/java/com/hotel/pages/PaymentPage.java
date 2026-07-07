package com.hotel.pages;

import com.hotel.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentPage extends BasePage {
    
    // Card Details
    @FindBy(id = "cardNumber")
    private WebElement cardNumber;
    
    @FindBy(id = "cardName")
    private WebElement cardName;
    
    @FindBy(id = "expiryMonth")
    private WebElement expiryMonth;
    
    @FindBy(id = "expiryYear")
    private WebElement expiryYear;
    
    @FindBy(id = "cvv")
    private WebElement cvv;
    
    // Billing Address
    @FindBy(id = "billingAddress")
    private WebElement billingAddress;
    
    @FindBy(id = "billingCity")
    private WebElement billingCity;
    
    @FindBy(id = "billingCountry")
    private WebElement billingCountry;
    
    // Payment Button
    @FindBy(css = ".btn-pay")
    private WebElement payNowButton;
    
    @FindBy(css = ".payment-error")
    private WebElement paymentError;
    
    public PaymentPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public ConfirmationPage makePayment(
            String cardNumber,
            String cardName,
            String expiryMonth,
            String expiryYear,
            String cvv) {
        
        sendKeys(this.cardNumber, cardNumber);
        sendKeys(this.cardName, cardName);
        selectDropdown(this.expiryMonth, expiryMonth);
        selectDropdown(this.expiryYear, expiryYear);
        sendKeys(this.cvv, cvv);
        
        click(payNowButton);
        logger.info("Payment made successfully");
        
        return new ConfirmationPage(driver);
    }
    
    public boolean isPaymentErrorDisplayed() {
        return isElementDisplayed(paymentError);
    }
}
